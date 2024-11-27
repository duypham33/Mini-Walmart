import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { ICartItem } from 'src/app/core/models/user/cart-item.model';
import { AppState } from 'src/app/core/store/app.state';
import { userCartSelector } from 'src/app/core/store/user/user.selector';
import { convertCartItemToShoppingItem } from 'src/app/core/utils/shopping-item.util';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ShoppingCartComponent {
  constructor(
    private store: Store<AppState>
  ){}
  cartItems$ = this.store.select(userCartSelector);

  shoppingItems(cartItems: ICartItem[]){
    return cartItems.map(i => convertCartItemToShoppingItem(i));
  }
}
