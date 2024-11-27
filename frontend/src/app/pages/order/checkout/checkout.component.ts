import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { delay, exhaustMap, map, mergeMap, of, take, tap } from 'rxjs';
import { ORDER_DETAIL_URL } from 'src/app/core/constants/app-urls.constant';
import { ICartItem } from 'src/app/core/models/user/cart-item.model';
import { OrderService } from 'src/app/core/services/order.service';
import { appStatusLoadingAction, appStatusOkWithInfoAction } from 'src/app/core/store/app-status/app-status.action';
import { AppState } from 'src/app/core/store/app.state';
import { userCleanCartAction } from 'src/app/core/store/user/user.action';
import { userCartSelector } from 'src/app/core/store/user/user.selector';
import { convertCartItemToShoppingItem, getTotalItems, parseInvoice } from 'src/app/core/utils/shopping-item.util';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CheckoutComponent {
  constructor(
    private store: Store<AppState>,
    private orderService: OrderService,
    private router: Router
  ){}

  cartItems$ = this.store.select(userCartSelector);
  data$ = this.cartItems$.pipe(
    mergeMap(items => this.orderService.previewCheckout().pipe(
      map(preview => ({
        cartItems: items, invoice: parseInvoice(preview)
      }))
    ))
  );

  shoppingItems(cartItems: ICartItem[]){
    return cartItems.map(i => convertCartItemToShoppingItem(i));
  }

  numItems(cartItems: ICartItem[]){
    return getTotalItems(cartItems);
  }

  checkout(){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.orderService.placeCheckout()),
      tap(order => {
        this.store.dispatch(appStatusOkWithInfoAction({
            info: `Successfully Checkout! Your Order is ready!`
        }));
        this.store.dispatch(userCleanCartAction());
        this.router.navigateByUrl(ORDER_DETAIL_URL.replace(":orderId", order.id.toString()));
      }),
      take(1)
    ).subscribe();
  }
}
