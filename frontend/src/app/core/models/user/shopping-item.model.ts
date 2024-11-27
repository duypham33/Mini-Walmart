export interface IShoppingItem{
    quantity: number,
    product: {
        id: number,
        name: string,
        retailPrice: number,
        imgPath?: string
    }
}