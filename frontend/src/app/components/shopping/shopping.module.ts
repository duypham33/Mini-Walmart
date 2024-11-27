import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShoppingListComponent } from './shopping-list/shopping-list.component';
import { ShoppingItemComponent } from './shopping-item/shopping-item.component';
import { ShoppingItemSummaryComponent } from './shopping-item-summary/shopping-item-summary.component';
import { ProductComponentModule } from 'src/app/pages/products/product-components/product-component.module';



@NgModule({
  declarations: [
    ShoppingListComponent,
    ShoppingItemComponent,
    ShoppingItemSummaryComponent
  ],
  exports: [
    ShoppingListComponent,
    ShoppingItemSummaryComponent
  ],
  imports: [
    CommonModule,
    ProductComponentModule
  ]
})
export class ShoppingModule { }
