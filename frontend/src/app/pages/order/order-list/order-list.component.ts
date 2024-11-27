import { ChangeDetectionStrategy, Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { combineLatest, delay, exhaustMap, filter, map, of, take, tap } from 'rxjs';
import { ORDER_DETAIL_URL, ORDER_LIST_URL } from 'src/app/core/constants/app-urls.constant';
import { USER_ROLE } from 'src/app/core/constants/user-role.constant';
import { IOrder } from 'src/app/core/models/order/order.model';
import { DEFAULT_ORDER_QUERY, IOrderQuery, ORDER_STATUSES } from 'src/app/core/models/query/order-query.model';
import { OrderService } from 'src/app/core/services/order.service';
import { appStatusLoadingAction, appStatusOkWithInfoAction } from 'src/app/core/store/app-status/app-status.action';
import { AppState } from 'src/app/core/store/app.state';
import { currentUserSelector } from 'src/app/core/store/user/user.selector';
import { getOrderParamsFromQuery, getOrderSearchQuery, getSelectedStatusFromQuery, parseParamsFromQuery } from 'src/app/core/utils/order.util';
import { convertOrderItemToShoppingItem, getOrderStatus, getOrderStatusIconPath, getTotalItemsForOrder, isOrderProcessing } from 'src/app/core/utils/shopping-item.util';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderListComponent {
  constructor(
    private store: Store<AppState>,
    private orderService: OrderService,
    private router: Router,
    private route: ActivatedRoute,
  ){}

  orderQuery: IOrderQuery = {...DEFAULT_ORDER_QUERY};
  filters: string[] = ORDER_STATUSES;
  selectedFilter: number = 0;

  isAdmin$ = this.store.select(currentUserSelector).pipe(
    map(user => {
      return user && user.role === USER_ROLE.ADMIN.toString(); 
    })
  );

  orders$ = combineLatest([this.isAdmin$, this.route.queryParams]).pipe(
    filter(([isAdmin, _]) => isAdmin !== undefined),
    exhaustMap(([isAdmin, params]) => {
      this.orderQuery = getOrderSearchQuery(params);
      this.selectedFilter = getSelectedStatusFromQuery(this.orderQuery);

      return this.orderService.getOrders(isAdmin!, parseParamsFromQuery(this.orderQuery));
    })
  );

  openCancelOrder: IOrder | null = null;

  onCloseCancel(){
    this.openCancelOrder = null;
  }

  onCancel(order: IOrder){
    this.openCancelOrder = order;
  }

  handleCancel(returnByECash: boolean){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.orderService.cancelOrder(
        this.openCancelOrder!.id, this.openCancelOrder!.orderingUser !== undefined, returnByECash
      )),
      tap(res => {
        this.store.dispatch(appStatusOkWithInfoAction({
          info: res.message ?? `Successfully canceled the order by ${returnByECash ? 'E-Cash' : 'Refund'}!`
        }));
        const id = this.openCancelOrder!.id;
        this.onCloseCancel();
        this.viewDetails(id);
      }),
      take(1)
    ).subscribe();
  }

  numItems(order: IOrder){
    return getTotalItemsForOrder(order.items);
  }

  parseDate(date: string){
    return date.split("T")[0];
  }

  onSelecteFilter(index: number){
    if(index === this.selectedFilter) return;
    this.orderQuery.page = 1;
    this.navigate(this.filters[index]);
  }

  navigatePage(dir: number){
    this.orderQuery.page += dir;
    this.navigate();
  }

  navigate(newStatus?: string){
    this.router.navigate([ORDER_LIST_URL], {
      queryParams: getOrderParamsFromQuery(this.orderQuery, newStatus ?? this.filters[this.selectedFilter])
    });
  }

  shoppingItems(order: IOrder){
    return order.items.map(i => convertOrderItemToShoppingItem(i));
  }

  getOrderStatus(status: string): string {
    return getOrderStatus(status);
  }

  getOrderStatusIconPath(status: string): string {
    return getOrderStatusIconPath(status);
  }

  isOrderProcessing(status: string): boolean{
    return isOrderProcessing(status);
  }

  viewDetails(id: number){
    this.router.navigateByUrl(ORDER_DETAIL_URL.replace(":orderId", id.toString()));
  }

  onComplete(orderId: number){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.orderService.completeOrder(orderId)),
      tap(res => {
        this.store.dispatch(appStatusOkWithInfoAction({info: res.message ?? "Successfully completed the order!"}));
        this.viewDetails(orderId);
      }),
      take(1)
    ).subscribe();
  }
}
