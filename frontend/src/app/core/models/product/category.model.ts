export interface ICategory{
    id: number,
    name: string
}

export interface ICategoryFamily{
    id: number,
    name: string,
    children: ICategory[]
}