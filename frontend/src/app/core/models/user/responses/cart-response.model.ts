import { ICartItem } from "../cart-item.model";

export interface ICartResponse{
    totalPrice: number,
    items: ICartItem[]
}