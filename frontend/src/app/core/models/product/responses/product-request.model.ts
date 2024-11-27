export interface IUpdateProductRequest{
    name: string,
    description: string,
    retailPrice: number,
    wholesalePrice: number,
    quantity: number
}

export interface ICreateProductRequest{
    name: string,
    description: string,
    retailPrice: number,
    wholesalePrice: number,
    quantity: number,
    categoryId: number,
    brandId: number
}