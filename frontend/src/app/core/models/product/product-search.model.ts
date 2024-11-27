import { IProductResponse } from "./responses/product-response.model";

export interface IProductSearch extends IProductResponse{
    message: string,
    priceRage: {
        min: number,
        max: number,
        step: number
    }
}