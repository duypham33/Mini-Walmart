import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CHECKOUT_API_URL, ORDER_ADMIN_API_URL, ORDER_API_URL, ORDER_CANCEL_ADMIN_API_URL, ORDER_CANCEL_API_URL, ORDER_COMPLETE_ADMIN_API_URL, ORDERS_ADMIN_API_URL, ORDERS_API_URL, PREVIEW_CHECKOUT_API_URL } from "../constants/api-urls.constant";
import { IApiResponse } from "../models/api-response.model";
import { ICheckout } from "../models/order/checkout.model";
import { map } from "rxjs";
import { IOrder, IOrderDetail } from "../models/order/order.model";
import { Params } from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class OrderService{
    constructor(private httpClient: HttpClient){}

    previewCheckout(){
        return this.httpClient.get<IApiResponse<ICheckout>>(
            PREVIEW_CHECKOUT_API_URL
        ).pipe(
            map(res => res.data)
        );
    }

    placeCheckout(){
        return this.httpClient.post<IApiResponse<IOrderDetail>>(
            CHECKOUT_API_URL, null
        ).pipe(
            map(res => res.data)
        );
    }

    getOrders(isAdmin: boolean, params: Params){
        return this.httpClient.get<IApiResponse<IOrder[]>>(
            isAdmin ? ORDERS_ADMIN_API_URL : ORDERS_API_URL, {params}
        ).pipe(
            map(res => res.data)
        );
    }

    getOrder(orderId: number, isAdmin: boolean){
        return this.httpClient.get<IApiResponse<IOrderDetail>>(
            (isAdmin ? ORDER_ADMIN_API_URL : ORDER_API_URL).replace(":orderId", orderId.toString())
        ).pipe(
            map(res => res.data)
        );
    }

    cancelOrder(orderId: number, isAdmin: boolean, returnByECash: boolean){
        return this.httpClient.patch<IApiResponse<IOrderDetail>>(
            (isAdmin ? ORDER_CANCEL_ADMIN_API_URL : ORDER_CANCEL_API_URL).replace(":orderId", orderId.toString()),
            null, {params: {returnByECash}}
        );
    }

    completeOrder(orderId: number){
        return this.httpClient.patch<IApiResponse<IOrderDetail>>(
            ORDER_COMPLETE_ADMIN_API_URL.replace(":orderId", orderId.toString()),
            null
        );
    }
}