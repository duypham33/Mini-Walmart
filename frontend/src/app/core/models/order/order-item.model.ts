export interface IOrderItem{
    orderItemId: number,
    productId: number,
    productName: string,
    productImgPath?: string,
    purchasedPrice: number,
    quantity: number
}