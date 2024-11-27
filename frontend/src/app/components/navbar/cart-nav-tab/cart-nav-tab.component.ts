import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { SHOPPING_CART_URL } from 'src/app/core/constants/app-urls.constant';
import { ICartItem } from 'src/app/core/models/user/cart-item.model';
import { AppState } from 'src/app/core/store/app.state';
import { userCartSelector } from 'src/app/core/store/user/user.selector';
import { getTotalItems, getTotalItemsCost } from 'src/app/core/utils/shopping-item.util';

@Component({
  selector: 'app-cart-nav-tab',
  templateUrl: './cart-nav-tab.component.html',
  styleUrls: ['./cart-nav-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CartNavTabComponent {
  constructor(private router: Router, private store: Store<AppState>){}
  cartItems$ = this.store.select(userCartSelector);

  navigateToShoppingCart(){
    this.router.navigateByUrl(SHOPPING_CART_URL);
  }

  numItems(cartItems: ICartItem[]){
    return getTotalItems(cartItems);
  }

  getTotalItemsCost(items: ICartItem[]){
    return getTotalItemsCost(items).toFixed(2);
  }
}
