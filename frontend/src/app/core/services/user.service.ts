import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { IApiResponse } from "../models/api-response.model";
import { ICartResponse } from "../models/user/responses/cart-response.model";
import { GET_CART_API_URL, GET_UPDATE_CART_API_URL, GET_WATCHLIST_API_URL, STATISTICS_ADMIN_API_URL, STATISTICS_API_URL, UPDATE_WATCHLIST_API_URL } from "../constants/api-urls.constant";
import { catchError, map, of } from "rxjs";
import { IProduct } from "../models/product/product.model";
import { IUpdateCartRequest } from "../models/user/requests/update-cart-item.model";
import { ICartItem } from "../models/user/cart-item.model";
import { IAdminStats, IUserStats } from "../models/user/user-stat.model";

@Injectable({
    providedIn: 'root'
})
export class UserService{
    constructor(private httpClient: HttpClient){}

    getCart(){
        return this.httpClient.get<IApiResponse<ICartResponse>>(
            GET_CART_API_URL
        ).pipe(
            map(res => res.data),
            catchError(_ => of({totalPrice: 0, items: []} as ICartResponse))
        );
    }

    updateCart(data: IUpdateCartRequest){
        return this.httpClient.patch<IApiResponse<ICartItem>>(
            GET_UPDATE_CART_API_URL, data
        ).pipe(
            map(res => {
                return {
                    quantity: data.quantity,
                    product: res.data.product
                } as ICartItem
            })
        );
    }

    getWatchList(){
        return this.httpClient.get<IApiResponse<IProduct[]>>(
            GET_WATCHLIST_API_URL
        ).pipe(
            map(res => res.data),
            catchError(_ => of([] as IProduct[]))
        );
    }

    updateWatchList(data: {productId: number, isAdd: boolean}){
        const url = UPDATE_WATCHLIST_API_URL.replace(":productId", data.productId.toString());
        return data.isAdd ? this.httpClient.post(url, null) : this.httpClient.delete(url);
    }

    getUserStatistics(){
        return this.httpClient.get<IApiResponse<IUserStats>>(
            STATISTICS_API_URL
        ).pipe(
            map(res => res.data)
        );
    }

    getAdminStatistics(){
        return this.httpClient.get<IApiResponse<IAdminStats>>(
            STATISTICS_ADMIN_API_URL
        ).pipe(
            map(res => res.data)
        );
    }
}