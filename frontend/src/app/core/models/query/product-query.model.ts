export interface IProductQuery{
    page: number,
    pageSize: number,
    search?: string,
    categoryId?: number,
    maxPrice?: number,
    brands?: number[]
}

export const DEFAULT_PRODUCT_QUERY: IProductQuery = {
    page: 1,
    pageSize: 25
}