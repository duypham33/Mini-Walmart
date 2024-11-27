import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { IProductResponse } from "../models/product/responses/product-response.model";
import { IApiResponse } from "../models/api-response.model";
import { CATEGORY_FAMILIES_API_URL, CATEGORY_ROOT_API_URL, CREATE_PRODUCT_API_URL, GET_PRODUCT_ADMIN_API_URL, GET_PRODUCT_API_URL, GET_PRODUCTS_ADMIN_API_URL, GET_PRODUCTS_API_URL, SEARCH_BRAND_API_URL, SEARCH_CATEGORY_API_URL, UPDATE_PRODUCT_API_URL } from "../constants/api-urls.constant";
import { Params } from "@angular/router";
import { map, take } from "rxjs";
import { pareseProductsBackend } from "../utils/product.util";
import { IProduct } from "../models/product/product.model";
import { ICreateProductRequest, IUpdateProductRequest } from "../models/product/responses/product-request.model";
import { IBrand } from "../models/product/brand.model";
import { ICategory, ICategoryFamily } from "../models/product/category.model";

@Injectable({
    providedIn: 'root'
})
export class ProductService{
    constructor(private httpClient: HttpClient){}

    getProducts(params: Params, isAdmin: boolean){
        // console.log(params);
        if(params["search"] === undefined) delete params["search"];
        return this.httpClient.get<IApiResponse<IProductResponse>>(
            isAdmin ? GET_PRODUCTS_ADMIN_API_URL : GET_PRODUCTS_API_URL,
            { params }
        ).pipe(
            map(res => pareseProductsBackend(res))
        );
    }

    getProduct(productId: number, isAdmin: boolean){
        // console.log(params);
        const url = isAdmin ? GET_PRODUCT_ADMIN_API_URL : GET_PRODUCT_API_URL;
        return this.httpClient.get<IApiResponse<IProduct>>(url.replace(":productId", productId.toString())).pipe(
            map(res => res.data)
        );
    }

    updateProduct(productId: number, data: IUpdateProductRequest){
        const url = UPDATE_PRODUCT_API_URL.replace(":productId", productId.toString());
        return this.httpClient.patch<IApiResponse<IProduct>>(url, data).pipe(
            map(res => res.data)
        );
    }

    createProduct(data: ICreateProductRequest){
        return this.httpClient.post<IApiResponse<IProduct>>(CREATE_PRODUCT_API_URL, data).pipe(
            map(res => res.data)
        );
    }

    searchCategories(kw: string){
        return this.httpClient.get<IApiResponse<{id: number, categoryPath: string}[]>>(
            SEARCH_CATEGORY_API_URL,
            { params: {kw} }
        ).pipe(
            map(res => {
                return res.data.map(data => ({
                    id: data.id,
                    categoryPath: data.categoryPath.split("-").slice(-1)[0].trim()
                }))
            })
        );
    }

    searchBrands(kw: string){
        return this.httpClient.get<IApiResponse<IBrand[]>>(
            SEARCH_BRAND_API_URL,
            { params: {kw} }
        ).pipe(
            map(res => res.data)
        );
    }

    getRootCategories(){
        return this.httpClient.get<IApiResponse<ICategory[]>>(
            CATEGORY_ROOT_API_URL
        ).pipe(
            map(res => res.data),
            take(1)
        );
    }

    getCategoryFamilies(rootId: number){
        return this.httpClient.get<IApiResponse<ICategoryFamily[]>>(
            CATEGORY_FAMILIES_API_URL.replace(":rootId", rootId.toString())
        ).pipe(
            map(res => res.data),
            take(1)
        );
    }
}