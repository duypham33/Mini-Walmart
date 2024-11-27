import { Params } from "@angular/router";
import { IProduct, IProductForm } from "../models/product/product.model";
import { DEFAULT_PRODUCT_QUERY, IProductQuery } from "../models/query/product-query.model";
import { IProductFilter } from "../models/filter/product-filter.model";
import { IProductResponse } from "../models/product/responses/product-response.model";
import { IApiResponse } from "../models/api-response.model";
import { IProductSearch } from "../models/product/product-search.model";
import { ICreateProductRequest, IUpdateProductRequest } from "../models/product/responses/product-request.model";

export const convertProductToProducForm = (product: IProduct): IProductForm => {
    return {
        ...product,
        category: product.category!,
        brand: product.brand!
    }
}

export const convertProductFromToProduct = (product: IProductForm): IProduct => {
    return {
        id: product.id,
        description: product.description,
        name: product.name!,
        quantity: product.quantity,
        retailPrice: product.retailPrice!,
        wholesalePrice: product.wholesalePrice,
        category: {
            id: product.category.id,
            categoryPath: product.category.categoryPath!
        },
        brand: {
            id: product.brand.id,
            name: product.brand.name!,
        }
    }
}

export const convertProductFromToUpdateProduct = (product: IProductForm): IUpdateProductRequest => {
    return {
        description: product.description!,
        name: product.name!,
        quantity: product.quantity!,
        retailPrice: product.retailPrice!,
        wholesalePrice: product.wholesalePrice!
    }
}

export const convertProductFromToCreateProduct = (product: IProductForm): ICreateProductRequest => {
    return {
        description: product.description!,
        name: product.name!,
        quantity: product.quantity!,
        retailPrice: product.retailPrice!,
        wholesalePrice: product.wholesalePrice!,
        categoryId: product.category.id,
        brandId: product.brand.id
    }
}

export const getProductPrizeIcon = (position: number): string => {
    if(position === 0) return "assets/icons/general/gold_ic.svg";
    if(position === 1) return "assets/icons/general/silver_ic.svg";
    return "assets/icons/general/brozen_ic.svg";
}

export const getProductSearchQuery = (params: Params): IProductQuery => {
    const res = {...DEFAULT_PRODUCT_QUERY};
    if(params["page"]) res.page = parseInt(params["page"]);
    if(params["pageSize"]) res.pageSize = parseInt(params["pageSize"]);

    if(params["search"]) res.search = params["search"];
    if(params["categoryId"]) res.categoryId = parseInt(params["categoryId"]);
    if(params["maxPrice"]) res.maxPrice = parseFloat(params["maxPrice"]);
    if(params["brands"]) res.brands = (params["brands"] as string).split(",").map(i => parseInt(i));
    return res;
}

export const getProductFilterFromQuery = (query: IProductQuery): IProductFilter => {
    return {
        maxPrice: query.maxPrice,
        brands: query.brands?.map(id => ({id})) ?? []
    }
}

export const getParamsFromProductFilter = (query: IProductQuery, filter: IProductFilter): Params => {
    const params: Params = {
        page: query.page,
        pageSize: query.pageSize,
        search: query.search
    }
    
    if(query.categoryId) params['categoryId'] = query.categoryId;
    if(filter.maxPrice) params['maxPrice'] = filter.maxPrice;
    if(filter.brands.length > 0) params['brands'] = filter.brands
    .filter(b => b.name).map(b => b.id.toString()).join(",");
    return params;
}

export const getParamsFromProductQuery = (query: IProductQuery): Params => {
    const params: Params = {
        page: query.page,
        pageSize: query.pageSize,
        search: query.search
    }
    
    if(query.categoryId) params['categoryId'] = query.categoryId;
    if(query.maxPrice) params['maxPrice'] = query.maxPrice;
    if(query.brands && query.brands.length > 0) params['brands'] = query.brands.map(b => b.toString()).join(",");
    return params;
}

export const pareseProductsBackend = (res: IApiResponse<IProductResponse>): IProductSearch => {
    const products = res.data.products;
    let min = 0, step = 0; 
    let max = products.length === 0 ? 1000 : products[0].retailPrice;
    products.forEach(p => {
        min = Math.min(min, p.retailPrice);
        max = Math.max(max, p.retailPrice);
    });

    if(max > 1000.0) {
        max = 10000;
        step = 100;
    }
    else if(max > 100.0) {
        max = 1000;
        step = 50;
    }
    else if(max > 10.0) {
        max = 100;
        step = 10;
    }
    else {
        max = 10;
        step = 1;
    }

    return {
        ...res.data,
        message: res.message!,
        priceRage: {
            min,
            max,
            step
        }
    }
}