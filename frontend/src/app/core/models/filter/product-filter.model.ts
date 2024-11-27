export interface IProductFilter{
    maxPrice?: number,
    brands: {id: number, name?: string}[]
}