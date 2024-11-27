import { environment } from "src/environments/environment";

const baseUrl = `${environment.backendRootUrl}`;

export const LOGIN_API_URL = `${baseUrl}/login`;
export const REGISTER_API_URL = `${baseUrl}/signup`;
export const CHECKAUTH_API_URL = `${baseUrl}/checkAuth`;
export const GET_CART_API_URL = `${baseUrl}/cart`;
export const GET_UPDATE_CART_API_URL = `${baseUrl}/update-cart`;
export const GET_WATCHLIST_API_URL = `${baseUrl}/watchlist/products/all`;
export const UPDATE_WATCHLIST_API_URL = `${baseUrl}/watchlist/product/:productId`;

export const GET_PRODUCTS_API_URL = `${baseUrl}/products/search`;
export const GET_PRODUCTS_ADMIN_API_URL = `${baseUrl}/admin/products/search`;
export const GET_PRODUCT_API_URL = `${baseUrl}/products/:productId`;
export const GET_PRODUCT_ADMIN_API_URL = `${baseUrl}/admin/products/:productId`;
export const UPDATE_PRODUCT_API_URL = `${baseUrl}/admin/products/:productId`;
export const CREATE_PRODUCT_API_URL = `${baseUrl}/admin/products`;
export const SEARCH_CATEGORY_API_URL = `${baseUrl}/products/category-search`;
export const CATEGORY_ROOT_API_URL = `${baseUrl}/products/category-root`;
export const CATEGORY_FAMILIES_API_URL = `${baseUrl}/products/category-families/:rootId`;
export const SEARCH_BRAND_API_URL = `${baseUrl}/products/brand-search`;

export const PREVIEW_CHECKOUT_API_URL = `${baseUrl}/orders/preview`;
export const CHECKOUT_API_URL = `${baseUrl}/orders`;
export const ORDERS_API_URL = `${baseUrl}/orders/all`;
export const ORDERS_ADMIN_API_URL = `${baseUrl}/admin/orders/all`;
export const ORDER_API_URL = `${baseUrl}/orders/:orderId`;
export const ORDER_ADMIN_API_URL = `${baseUrl}/admin/orders/:orderId`;
export const ORDER_CANCEL_API_URL = `${baseUrl}/orders/:orderId/cancel`;
export const ORDER_CANCEL_ADMIN_API_URL = `${baseUrl}/admin/orders/:orderId/cancel`;
export const ORDER_COMPLETE_ADMIN_API_URL = `${baseUrl}/admin/orders/:orderId/complete`;

export const STATISTICS_API_URL = `${baseUrl}/statistics`;
export const STATISTICS_ADMIN_API_URL = `${baseUrl}/admin/statistics`;