import { RouterModule, Routes } from "@angular/router";
import { ProductListComponent } from "./product-list/product-list.component";
import { NgModule } from "@angular/core";
import { PRODUCTS_DETAIL_PATH, PRODUCTS_LIST_PATH } from "src/app/core/constants/app-urls.constant";
import { ProductDetailComponent } from "./product-detail/product-detail.component";

const routes: Routes = [
    {path: PRODUCTS_LIST_PATH, component: ProductListComponent},
    {path: PRODUCTS_DETAIL_PATH, component: ProductDetailComponent}
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class ProductsRoutingModule{}