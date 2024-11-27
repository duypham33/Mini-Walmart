export interface IOrderQuery{
    page: number,
    pageSize: number,
    status?: string
}

export const DEFAULT_ORDER_QUERY: IOrderQuery = {
    page: 1,
    pageSize: 5
}

export const ORDER_STATUSES = ["All", "Completed", "On Going", "Canceled"];