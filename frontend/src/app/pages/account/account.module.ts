import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserHomeComponent } from './user-home/user-home.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AccountRoutingModule } from './account-routing.module';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { ShoppingModule } from 'src/app/components/shopping/shopping.module';
import { ComponentsModule } from 'src/app/components/components.module';
import { ProductComponentModule } from '../products/product-components/product-component.module';
import { HomeComponent } from './home/home.component';
import { WatchlistComponent } from './watchlist/watchlist.component';

@NgModule({
  declarations: [
    UserHomeComponent,
    AdminHomeComponent,
    ShoppingCartComponent,
    HomeComponent,
    WatchlistComponent
  ],
  imports: [
    CommonModule,
    AccountRoutingModule,
    ShoppingModule,
    ProductComponentModule,
    ComponentsModule
  ]
})
export class AccountModule { }
