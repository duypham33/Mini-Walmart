import { ChangeDetectionStrategy, ChangeDetectorRef, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { debounceTime, delay, exhaustMap, of, Subject, Subscription, take, tap } from 'rxjs';
import { PRODUCTS_DETAIL_URL } from 'src/app/core/constants/app-urls.constant';
import { IBrand } from 'src/app/core/models/product/brand.model';
import { EMPTY_PRODUCT, IProduct, IProductForm } from 'src/app/core/models/product/product.model';
import { ProductService } from 'src/app/core/services/product.service';
import { appStatusLoadingAction, appStatusOkWithInfoAction } from 'src/app/core/store/app-status/app-status.action';
import { AppState } from 'src/app/core/store/app.state';
import { convertProductFromToCreateProduct, convertProductFromToUpdateProduct, convertProductToProducForm } from 'src/app/core/utils/product.util';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductFormComponent implements OnInit, OnDestroy{
  focusField: string | null = null;
  isReady: boolean = false;
  @Input() productDetail!: IProduct | null;
  product: IProductForm = EMPTY_PRODUCT;

  constructor(
    private productService: ProductService, 
    private router: Router, 
    private chRef: ChangeDetectorRef,
    private store: Store<AppState>
  ){}
  
  productSubcription?: Subscription;
  ngOnInit(): void {
    // console.log(this.productDetail)
    if(!this.productDetail || this.productDetail.brand) {
      if(this.productDetail) this.product = convertProductToProducForm(this.productDetail);
      this.isReady = true;
      this.chRef.detectChanges();
      return;
    }

    if(this.productSubcription) return;
    this.productSubcription = this.productService.getProduct(this.productDetail!.id, true).pipe(
      tap(p => {
        this.product = convertProductToProducForm(p);
        this.isReady = true;
        this.chRef.detectChanges();
      })
    ).subscribe();
  }

  ngOnDestroy(): void {
    this.productSubcription?.unsubscribe();
    this.productSubcription = undefined;
  }

  get isUpdateMode(){
    return this.product.id > 0;
  }

  onFocus(field: string){
    this.focusField = field;
  }

  onBlur(field: string){
    if(this.focusField === field) this.focusField = null;
  }
  
  @Output() onUpdate = new EventEmitter<IProduct>();
  onSubmit(productForm: NgForm){
    // console.log(productForm);
    if(this.isUpdateMode) this.handleUpdate();
    else this.handleCreate();
  }

  handleUpdate(){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.productService.updateProduct(
        this.product.id, convertProductFromToUpdateProduct(this.product)
      )),
      tap(p => {
        this.store.dispatch(appStatusOkWithInfoAction({
            info: `Successfully updated the product!`
        }));
        this.onUpdate.emit(p);
      }),
      take(1)
    ).subscribe();
  }
  
  @Output() onCreated = new EventEmitter<void>();
  handleCreate(){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.productService.createProduct(
        convertProductFromToCreateProduct(this.product)
      )),
      tap(p => {
        this.store.dispatch(appStatusOkWithInfoAction({
            info: `Successfully create a new product!`
        }));
        this.router.navigateByUrl(PRODUCTS_DETAIL_URL.replace(":productId", p.id.toString()));
        this.onCreated.emit();
      }),
      take(1)
    ).subscribe();
  }

  isInValid(control: NgModel): boolean{
    return control.touched && control.invalid ? true : false;
  }

  getError(field: string, errors: {[key: string]: any}): string{
    if(errors["required"]) return field + " is required!";
    if(errors["minlength"]) return `${field} must have at least ${errors["minlength"].requiredLength} characters!`;
    if(errors["min"]) return `${field} must be greater than or equal $${errors["min"].min}!`;
    return `${field} is invalid!`;
  }
  
  brandKwSubject = new Subject<string>();
  brands$ = this.brandKwSubject.asObservable().pipe(
    debounceTime(1000),
    exhaustMap(kw => kw ? this.productService.searchBrands(kw) : of([]))
  );
  onBrandKw(evt: Event){
    // console.log((evt.target as HTMLInputElement).value)
    if(this.isUpdateMode) return;
    const v = (evt.target as HTMLInputElement).value.trim();
    if(!v) this.brandKwSubject.next('');
    else this.brandKwSubject.next((evt.target as HTMLInputElement).value);
  }
  onSelectBrand(brand: IBrand){
    this.product.brand = {...brand};
  }
  closeBrand(){
    this.brandKwSubject.next('');
  }

  categoryKwSubject = new Subject<string>();
  categories$ = this.categoryKwSubject.asObservable().pipe(
    debounceTime(1000),
    exhaustMap(kw => kw ? this.productService.searchCategories(kw) : of([]))
  );
  onCategoryKw(evt: Event){
    // console.log((evt.target as HTMLInputElement).value)
    if(this.isUpdateMode) return;
    const v = (evt.target as HTMLInputElement).value.trim();
    if(!v) this.categoryKwSubject.next('');
    else this.categoryKwSubject.next((evt.target as HTMLInputElement).value);
  }
  onSelectCategory(category: {id: number, categoryPath: string}){
    this.product.category = {...category};
  }
  closeCategory(){
    this.categoryKwSubject.next('');
  }
}
