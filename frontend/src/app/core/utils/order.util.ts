import { Params } from "@angular/router";
import { DEFAULT_ORDER_QUERY, IOrderQuery, ORDER_STATUSES } from "../models/query/order-query.model";

export const getOrderSearchQuery = (params: Params): IOrderQuery => {
    const res = {...DEFAULT_ORDER_QUERY};
    if(params["page"]) res.page = parseInt(params["page"]);
    if(params["pageSize"]) res.pageSize = parseInt(params["pageSize"]);

    if(params["status"]) res.status = params["status"];
    return res;
}

export const getSelectedStatusFromQuery = (query: IOrderQuery) => {
    return ORDER_STATUSES.findIndex(s => s === convertStatusFromQuery(query.status));
}

const convertStatusFromQuery = (status?: string) => {
    if(!status || status === "ALL") return "All";
    if(status === "PREPARING" || status === "PROCESSING") return "On Going";
    if(status === "COMPLETED") return "Completed";
    if(status === "CANCELED" || status === "CANCEL") return "Canceled";
    return "";
}

const convertQueryStatusFromUiStatus = (status: string) => {
    if(status === "All") return undefined;
    if(status === "On Going") return "PROCESSING";
    if(status === "Completed") return "COMPLETED";
    if(status === "Canceled") return "CANCELED";
    return undefined;
}

const convertApiParamsStatusFromQuery = (status: string) => {
    if(status === "All" || status === "ALL") return undefined;
    if(status === "PREPARING" || status === "PROCESSING") return "PREPARING";
    if(status === "Completed") return "COMPLETED";
    if(status === "CANCELED" || status === "CANCEL") return "CANCEL";
    return status;
}

export const getOrderParamsFromQuery = (query: IOrderQuery, newStatus: string) => {
    const params: Params = {
        page: query.page,
        pageSize: query.pageSize
    }

    const status = convertQueryStatusFromUiStatus(newStatus);
    if(status) params["status"] = status;
    return params;
}

export const parseParamsFromQuery = (query: IOrderQuery) => {
    const params: Params = {
        page: query.page,
        pageSize: query.pageSize
    }
    
    if(!query.status) return params;
    const status = convertApiParamsStatusFromQuery(query.status);
    if(status) params["status"] = status;
    
    return params;
}