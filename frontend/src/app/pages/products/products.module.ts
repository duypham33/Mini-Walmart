import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductsRoutingModule } from './products-routing.module';
import { ProductFiltersComponent } from './product-list/product-filters/product-filters.component';
import { FormsModule } from '@angular/forms';
import { SelectedFiltersComponent } from './product-list/selected-filters/selected-filters.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ComponentsModule } from 'src/app/components/components.module';
import { ProductComponentModule } from './product-components/product-component.module';

@NgModule({
  declarations: [
    ProductListComponent,
    ProductFiltersComponent,
    SelectedFiltersComponent,
    ProductDetailComponent
  ],
  imports: [
    CommonModule,
    ProductsRoutingModule,
    FormsModule,
    ProductComponentModule,
    ComponentsModule
  ]
})
export class ProductsModule { }
