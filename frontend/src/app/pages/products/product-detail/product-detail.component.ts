import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { combineLatest, exhaustMap, filter, map, merge, Subject } from 'rxjs';
import { DEFAULT_PRODUCT_ICON_PATH } from 'src/app/core/constants/icon_paths.constant';
import { USER_ROLE } from 'src/app/core/constants/user-role.constant';
import { IProduct } from 'src/app/core/models/product/product.model';
import { ICartItem } from 'src/app/core/models/user/cart-item.model';
import { ProductService } from 'src/app/core/services/product.service';
import { AppState } from 'src/app/core/store/app.state';
import { userUpdateCartAction, userUpdateWatchListAction } from 'src/app/core/store/user/user.action';
import { currentUserSelector, userCartSelector, userWatchListSelector } from 'src/app/core/store/user/user.selector';
import { convertProductToProducForm } from 'src/app/core/utils/product.util';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductDetailComponent {
  openEdit: boolean = false;

  constructor(
    private store: Store<AppState>,
    private route: ActivatedRoute, 
    private productService: ProductService
  ){}

  productId$ = this.route.params.pipe(
    filter(param => param["productId"]),
    map(param => parseInt(param["productId"]))
  );
  
  isAdmin$ = this.store.select(currentUserSelector).pipe(
    map(user => {
      return user && user.role === USER_ROLE.ADMIN.toString(); 
    })
  );
  productDetail$ = combineLatest([this.isAdmin$, this.productId$]).pipe(
    filter(([isAdmin, _]) => isAdmin !== undefined),
    exhaustMap(([isAdmin, productId]) => {
      return this.productService.getProduct(
        productId,
        isAdmin!
      );
  }));
  
  updatedProductSubject = new Subject<IProduct>();
  updatedProduct$ = this.updatedProductSubject.asObservable();
  onProductUpdate(newProduct: IProduct){
    this.updatedProductSubject.next(newProduct);
    this.toggleOpenEdit();
  }

  product$ = merge(this.productDetail$, this.updatedProduct$);
  

  imgPath(product: IProduct): string{
    return product.imgPath ?? DEFAULT_PRODUCT_ICON_PATH;
  }


  cartItems$ = this.store.select(userCartSelector);
  watchList$ = this.store.select(userWatchListSelector);

  getCartItemQuantity(cartItems: ICartItem[], productId: number){
    return cartItems.find(item => item.product.id === productId)?.quantity ?? 0;
  }

  onUpdate(unit: number, cartItems: ICartItem[], productId: number){
    this.store.dispatch(userUpdateCartAction({
      productId,
      quantity: this.getCartItemQuantity(cartItems, productId) + unit
    }));
  }

  watchListImg(watchList: number[], productId: number){
    return watchList.includes(productId) 
    ? "assets/icons/general/heart_ic.svg" 
    : "assets/icons/general/heart-outline_ic.svg";
  }

  watchListText(watchList: number[], productId: number){
    return watchList.includes(productId) ? "Remove from list" : "Add to list";
  }

  updateWatchList(watchList: number[], productId: number){
    this.store.dispatch(userUpdateWatchListAction({
      productId,
      isAdd: !watchList.includes(productId)
    }));
  }


  editable(product: IProduct) {
    return product.quantity !== undefined;
  }

  toggleOpenEdit() {
    this.openEdit = !this.openEdit;
  }
  
  productForm(product: IProduct){
    return convertProductToProducForm(product);
  }
}
