import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { PRODUCTS_DETAIL_URL } from 'src/app/core/constants/app-urls.constant';
import { DEFAULT_PRODUCT_ICON_PATH } from 'src/app/core/constants/icon_paths.constant';
import { IShoppingItem } from 'src/app/core/models/user/shopping-item.model';
import { AppState } from 'src/app/core/store/app.state';
import { userUpdateCartAction, userUpdateWatchListAction } from 'src/app/core/store/user/user.action';
import { userWatchListSelector } from 'src/app/core/store/user/user.selector';

@Component({
  selector: 'app-shopping-item',
  templateUrl: './shopping-item.component.html',
  styleUrls: ['./shopping-item.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ShoppingItemComponent {
  @Input() item!: IShoppingItem;
  @Input() noActions: boolean = false;

  constructor(private store: Store<AppState>, private router: Router){}

  get imgPath(){
    return this.item.product.imgPath ?? DEFAULT_PRODUCT_ICON_PATH;
  }

  get total(){
    return this.item.product.retailPrice * this.item.quantity;
  }

  viewProductDetail(){
    this.router.navigateByUrl(PRODUCTS_DETAIL_URL.replace(":productId", this.item.product.id.toString()));
  }

  onUpdate(unit: number){
    this.store.dispatch(userUpdateCartAction({
      productId: this.item.product.id,
      quantity: this.item.quantity + unit
    }));
  }

  onRemove(){
    this.store.dispatch(userUpdateCartAction({
      productId: this.item.product.id,
      quantity: 0
    }));
  }
  
  watchList$ = this.store.select(userWatchListSelector);
  watchListText(watchList: number[]){
    return watchList.includes(this.item.product.id) ? "Remove from list" : "Add to list";
  }

  updateWatchList(watchList: number[]){
    this.store.dispatch(userUpdateWatchListAction({
      productId: this.item.product.id,
      isAdd: !watchList.includes(this.item.product.id)
    }));
  }
}
