export interface IApiResponse<T>{
    isSuccess: boolean,
    data: T,
    message?: string
}