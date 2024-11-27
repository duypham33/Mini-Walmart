import { IBrand } from "../brand.model"
import { IProduct } from "../product.model"

export interface IProductResponse{
    category?: {
        id: number,
        categoryPath: string
    },
    brands?: IBrand[],
    products: IProduct[]
}