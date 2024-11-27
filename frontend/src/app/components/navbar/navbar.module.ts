import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar.component';
import { AddressNavTabComponent } from './address-nav-tab/address-nav-tab.component';
import { OrderNavTabComponent } from './order-nav-tab/order-nav-tab.component';
import { AccountNavTabComponent } from './account-nav-tab/account-nav-tab.component';
import { CartNavTabComponent } from './cart-nav-tab/cart-nav-tab.component';
import { NavSearchComponent } from './nav-search/nav-search.component';
import { NavCategoriesComponent } from './nav-categories/nav-categories.component';
import { PrimaryNavTabComponent } from './primary-nav-tab/primary-nav-tab.component';
import { CategoryTabComponent } from './nav-categories/category-tab/category-tab.component';
import { AuthNavbarComponent } from './auth-navbar/auth-navbar.component';
import { ComponentsModule } from '../components.module';
import { ProductComponentModule } from 'src/app/pages/products/product-components/product-component.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    NavbarComponent,
    AddressNavTabComponent,
    OrderNavTabComponent,
    AccountNavTabComponent,
    CartNavTabComponent,
    NavSearchComponent,
    NavCategoriesComponent,
    PrimaryNavTabComponent,
    CategoryTabComponent,
    AuthNavbarComponent,
  ],
  exports: [
    NavbarComponent,
    AuthNavbarComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ComponentsModule,
    ProductComponentModule
  ]
})
export class NavbarModule { }
