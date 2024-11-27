import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ORDER_CHECKOUT_PATH, ORDER_DETAIL_PATH, ORDER_LIST_PATH } from "src/app/core/constants/app-urls.constant";
import { CheckoutComponent } from "./checkout/checkout.component";
import { OrderListComponent } from "./order-list/order-list.component";
import { OrderDetailComponent } from "./order-detail/order-detail.component";

const routes: Routes = [
    {path: ORDER_LIST_PATH, component: OrderListComponent},
    {path: ORDER_CHECKOUT_PATH, component: CheckoutComponent},
    {path: ORDER_DETAIL_PATH, component: OrderDetailComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class OrderRoutingModule{}