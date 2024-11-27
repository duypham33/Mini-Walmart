import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { PRODUCTS_DETAIL_URL } from 'src/app/core/constants/app-urls.constant';
import { DEFAULT_PRODUCT_ICON_PATH } from 'src/app/core/constants/icon_paths.constant';
import { IProduct } from 'src/app/core/models/product/product.model';
import { ICartItem } from 'src/app/core/models/user/cart-item.model';
import { IProductPrize } from 'src/app/core/models/user/user-stat.model';
import { AppState } from 'src/app/core/store/app.state';
import { userUpdateCartAction, userUpdateWatchListAction } from 'src/app/core/store/user/user.action';
import { userCartSelector, userWatchListSelector } from 'src/app/core/store/user/user.selector';
import { getProductPrizeIcon } from 'src/app/core/utils/product.util';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductCardComponent {
  @Input() product!: IProduct;
  @Input() productPrize?: IProductPrize;
  openEdit: boolean = false;

  constructor(private store: Store<AppState>, private router: Router){}

  cartItems$ = this.store.select(userCartSelector);
  watchList$ = this.store.select(userWatchListSelector);

  getCartItemQuantity(cartItems: ICartItem[]){
    return cartItems.find(item => item.product.id === this.product.id)?.quantity ?? 0;
  }

  onUpdate(unit: number, cartItems: ICartItem[]){
    this.store.dispatch(userUpdateCartAction({
      productId: this.product.id,
      quantity: this.getCartItemQuantity(cartItems) + unit
    }));
  }

  get imgPath(): string{
    return this.product.imgPath ?? DEFAULT_PRODUCT_ICON_PATH;
  }

  watchListImg(watchList: number[]){
    return watchList.includes(this.product.id) 
    ? "assets/icons/general/heart_ic.svg" 
    : "assets/icons/general/heart-outline_ic.svg"
  }
  
  // @Output() onRemovedWatchList = new EventEmitter<void>();
  updateWatchList(watchList: number[]){
    const isAdd = !watchList.includes(this.product.id);
    this.store.dispatch(userUpdateWatchListAction({
      productId: this.product.id,
      isAdd
    }));
    // this.onRemovedWatchList.emit();
  }

  toggleOpenEdit() {
    this.openEdit = !this.openEdit;
  }

  get editable() {
    return this.product.quantity !== undefined;
  }

  onProductUpdate(newProduct: IProduct){
    this.product = newProduct;
    this.toggleOpenEdit();
  }

  viewProductDetail(){
    this.router.navigateByUrl(PRODUCTS_DETAIL_URL.replace(":productId", this.product.id.toString()));
  }

  getPrizeIcon(position: number){
    return getProductPrizeIcon(position);
  }
}
