import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import { HOME_USER_PATH, SHOPPING_CART_PATH, WATCHLIST_PATH } from "src/app/core/constants/app-urls.constant";
import { ShoppingCartComponent } from "./shopping-cart/shopping-cart.component";
import { HomeComponent } from "./home/home.component";
import { WatchlistComponent } from "./watchlist/watchlist.component";

const routes: Routes = [
    {path: HOME_USER_PATH, component: HomeComponent},
    // {path: HOME_ADMIN_PATH, component: AdminHomeComponent},
    {path: WATCHLIST_PATH, component: WatchlistComponent},
    {path: SHOPPING_CART_PATH, component: ShoppingCartComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class AccountRoutingModule{}