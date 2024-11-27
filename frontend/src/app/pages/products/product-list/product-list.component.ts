import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { combineLatest, exhaustMap, filter, map, tap } from 'rxjs';
import { PRODUCTS_LIST_URL } from 'src/app/core/constants/app-urls.constant';
import { USER_ROLE } from 'src/app/core/constants/user-role.constant';
import { IProductFilter } from 'src/app/core/models/filter/product-filter.model';
import { IBrand } from 'src/app/core/models/product/brand.model';
import { IProduct } from 'src/app/core/models/product/product.model';
import { DEFAULT_PRODUCT_QUERY, IProductQuery } from 'src/app/core/models/query/product-query.model';
import { ProductService } from 'src/app/core/services/product.service';
import { AppState } from 'src/app/core/store/app.state';
import { currentUserSelector } from 'src/app/core/store/user/user.selector';
import { getParamsFromProductFilter, getParamsFromProductQuery, getProductFilterFromQuery, getProductSearchQuery } from 'src/app/core/utils/product.util';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductListComponent {
  productQuery: IProductQuery = {...DEFAULT_PRODUCT_QUERY};
  productFilter: IProductFilter = {brands: []};
  products: IProduct[] = [];
  resultMessage: string = "";
  brands: IBrand[] = [];
  priceRage: {
    min: number,
    max: number,
    step: number
  } = {min: 0, max: 1000, step: 10}

  constructor(
    private store: Store<AppState>,
    private route: ActivatedRoute, 
    private productService: ProductService,
    private router: Router, 
    private chRef: ChangeDetectorRef
  ){}

  isAdmin$ = this.store.select(currentUserSelector).pipe(
    map(user => {
      return user && user.role === USER_ROLE.ADMIN.toString(); 
    })
  );

  products$ = combineLatest([this.isAdmin$, this.route.queryParams]).pipe(
    filter(([isAdmin, _]) => isAdmin !== undefined),
    exhaustMap(([isAdmin, params]) => {
      // console.log(params);
      this.productQuery = getProductSearchQuery(params);
      this.productFilter = getProductFilterFromQuery(this.productQuery);
      
      return this.productService.getProducts(
        getParamsFromProductQuery(this.productQuery),
        isAdmin!
      );
    }),
    tap(res => {
      this.products = res.products;
      this.resultMessage = res.message;
      this.priceRage = res.priceRage;
      this.brands = res.brands ?? [];
      this.productFilter = {
        ...this.productFilter,
        brands: this.brands
        .filter(b => this.productFilter.brands.findIndex(br => br.id === b.id) >= 0)
      }

      this.chRef.detectChanges();
    })
  );

  onFilter(){
    this.productQuery.page = 1;
    this.navigate();
  }

  navigate(){
    this.router.navigate([PRODUCTS_LIST_URL], {
      queryParams: getParamsFromProductFilter(this.productQuery, this.productFilter)
    });
  }

  removeMaxPrice(){
    this.productFilter = {brands: this.productFilter.brands}
  }

  removeBrand(brandId: number){
    this.productFilter = {
      ...this.productFilter,
      brands: this.productFilter.brands.filter(b => b.id !== brandId)
    }
  }

  clearAllFilter(){
    this.productFilter = {brands: []};
  }

  navigatePage(dir: number){
    this.productQuery.page += dir;
    this.navigate();
  }
}
