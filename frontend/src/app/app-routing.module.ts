import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { isAuthenticatedGuard } from './core/guards/isAuthenticated.guard';
import { AUTH_PAGES_ROOT, ACCOUNT_PAGES_ROOT, PRODUCTS_PAGES_ROOT, ORDER_PAGES_ROOT } from './core/constants/app-urls.constant';

const routes: Routes = [
  {
    path: ACCOUNT_PAGES_ROOT, canActivateChild: [isAuthenticatedGuard],
    loadChildren: () => import('./pages/account/account.module').then(m => m.AccountModule)
  },
  {path: AUTH_PAGES_ROOT, loadChildren: () => import('./pages/auth/auth.module').then(m => m.AuthModule)},
  {
    path: PRODUCTS_PAGES_ROOT, canActivateChild: [isAuthenticatedGuard],
    loadChildren: () => import('./pages/products/products.module').then(m => m.ProductsModule)
  },
  {
    path: ORDER_PAGES_ROOT, canActivateChild: [isAuthenticatedGuard],
    loadChildren: () => import('./pages/order/order.module').then(m => m.OrderModule)
  },
  {path: '**', redirectTo: '/'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
