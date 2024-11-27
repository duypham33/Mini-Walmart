import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngrx/store';
import { combineLatest, delay, exhaustMap, filter, map, merge, of, Subject, take, tap } from 'rxjs';
import { USER_ROLE } from 'src/app/core/constants/user-role.constant';
import { IOrderDetail } from 'src/app/core/models/order/order.model';
import { OrderService } from 'src/app/core/services/order.service';
import { appStatusLoadingAction, appStatusOkWithInfoAction } from 'src/app/core/store/app-status/app-status.action';
import { AppState } from 'src/app/core/store/app.state';
import { currentUserSelector } from 'src/app/core/store/user/user.selector';
import { convertOrderItemToShoppingItem, getOrderStatus, getOrderStatusIconPath, getTotalItemsForOrder, isOrderProcessing, parseInvoiceForOrder, shortOrderNum } from 'src/app/core/utils/shopping-item.util';

@Component({
  selector: 'app-order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderDetailComponent {
  constructor(
    private store: Store<AppState>,
    private orderService: OrderService,
    private route: ActivatedRoute
  ){}

  isAdmin$ = this.store.select(currentUserSelector).pipe(
    map(user => {
      return user && user.role === USER_ROLE.ADMIN.toString(); 
    })
  );

  orderId$ = this.route.params.pipe(
    filter(param => param["orderId"]),
    map(param => parseInt(param["orderId"]))
  );

  data$ = combineLatest([this.isAdmin$, this.orderId$]).pipe(
    filter(([isAdmin, _]) => isAdmin !== undefined),
    exhaustMap(([isAdmin, orderId]) => {
      return merge(this.orderService.getOrder(
        orderId,
        isAdmin!
      ), this.updatedOrder$);
    }),
    map(order => ({
      order, invoice: parseInvoiceForOrder(order)
    }))
  );

  openCancel: boolean = false;

  numItems(order: IOrderDetail){
    return getTotalItemsForOrder(order.items);
  }

  shoppingItems(order: IOrderDetail){
    return order.items.map(i => convertOrderItemToShoppingItem(i));
  }

  orderNum(order: IOrderDetail): string{
    return shortOrderNum(order.orderNumber);
  }

  parseDate(date: string){
    return date.split("T")[0];
  }

  orderStatus(order: IOrderDetail): string {
    return getOrderStatus(order.status);
  }

  orderStatusIconPath(order: IOrderDetail): string {
    return getOrderStatusIconPath(order.status);
  }

  isOrderProcessing(order: IOrderDetail){
    return isOrderProcessing(order.status);
  }
  
  updatedOrderSubject = new Subject<IOrderDetail>();
  updatedOrder$ = this.updatedOrderSubject.asObservable();
  onComplete(orderId: number){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.orderService.completeOrder(orderId)),
      tap(res => {
        this.store.dispatch(appStatusOkWithInfoAction({info: res.message ?? "Successfully completed the order!"}));
        this.updatedOrderSubject.next(res.data);
      }),
      take(1)
    ).subscribe();
  }

  onCancel(){
    this.openCancel = true;
  }

  onCloseCancel(){
    this.openCancel = false;
  }

  handleCancel(order: IOrderDetail, returnByECash: boolean){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.orderService.cancelOrder(order.id, order.orderingUser !== undefined, returnByECash)),
      tap(res => {
        this.store.dispatch(appStatusOkWithInfoAction({
          info: res.message ?? `Successfully canceled the order by ${returnByECash ? 'E-Cash' : 'Refund'}!`
        }));
        this.updatedOrderSubject.next(res.data);
        this.onCloseCancel();
      }),
      take(1)
    ).subscribe();
  }
}
