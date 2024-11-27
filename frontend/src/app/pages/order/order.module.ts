import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckoutComponent } from './checkout/checkout.component';
import { ShoppingModule } from 'src/app/components/shopping/shopping.module';
import { OrderRoutingModule } from './order-routing.module';
import { OrderListComponent } from './order-list/order-list.component';
import { OrderDetailComponent } from './order-detail/order-detail.component';
import { ComponentsModule } from 'src/app/components/components.module';
import { CancelOrderComponent } from './cancel-order/cancel-order.component';



@NgModule({
  declarations: [
    CheckoutComponent,
    OrderListComponent,
    OrderDetailComponent,
    CancelOrderComponent
  ],
  imports: [
    CommonModule,
    ShoppingModule,
    OrderRoutingModule,
    ComponentsModule
  ]
})
export class OrderModule { }
