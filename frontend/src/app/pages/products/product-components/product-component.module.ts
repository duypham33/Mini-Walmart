import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductPricesComponent } from './product-prices/product-prices.component';
import { ProductRatingComponent } from './product-rating/product-rating.component';
import { ProductActionButtonComponent } from './product-action-button/product-action-button.component';
import { ProductCardComponent } from './product-card/product-card.component';
import { ProductFormComponent } from './product-form/product-form.component';
import { ComponentsModule } from 'src/app/components/components.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ProductPricesComponent,
    ProductRatingComponent,
    ProductActionButtonComponent,
    ProductCardComponent,
    ProductFormComponent
  ],
  exports: [
    ProductPricesComponent,
    ProductRatingComponent,
    ProductActionButtonComponent,
    ProductCardComponent,
    ProductFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ComponentsModule
  ]
})
export class ProductComponentModule { }
