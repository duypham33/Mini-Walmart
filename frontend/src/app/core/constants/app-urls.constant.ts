const formatPath = (path: string, isFirst?: boolean): string => {
    const p = path.trim();
    if(p) return (isFirst ? "" : "/") + p.trim();
    return "";
}

export const AUTH_PAGES_ROOT = "auth";
export const AUTH_LOGIN_PATH = "login";
export const AUTH_REGISTER_PATH = "register";
export const LOGIN_URL = `/${formatPath(AUTH_PAGES_ROOT)}${formatPath(AUTH_LOGIN_PATH)}`;
export const REGISTER_URL = `/${formatPath(AUTH_PAGES_ROOT)}${formatPath(AUTH_REGISTER_PATH)}`;

export const ACCOUNT_PAGES_ROOT = "";
export const HOME_USER_PATH = "";
// export const HOME_ADMIN_PATH = "admin";
export const SHOPPING_CART_PATH = "cart";
export const USER_HOME_URL = `/${formatPath(ACCOUNT_PAGES_ROOT)}${formatPath(HOME_USER_PATH)}`;
// export const ADMIN_HOME_URL = `/${formatPath(ACCOUNT_PAGES_ROOT)}${formatPath(HOME_ADMIN_PATH)}`;
export const SHOPPING_CART_URL = `/${formatPath(ACCOUNT_PAGES_ROOT)}${formatPath(SHOPPING_CART_PATH)}`;
export const WATCHLIST_PATH = "watchlist";
export const WATCHLIST_PATH_URL = `/${formatPath(ACCOUNT_PAGES_ROOT)}${formatPath(WATCHLIST_PATH)}`;

export const PRODUCTS_PAGES_ROOT = "products";
export const PRODUCTS_LIST_PATH = "";
export const PRODUCTS_DETAIL_PATH = ":productId";
export const PRODUCTS_LIST_URL = `/${formatPath(PRODUCTS_PAGES_ROOT)}${formatPath(PRODUCTS_LIST_PATH)}`;
export const PRODUCTS_DETAIL_URL = `/${formatPath(PRODUCTS_PAGES_ROOT)}${formatPath(PRODUCTS_DETAIL_PATH)}`;

export const ORDER_PAGES_ROOT = "orders";
export const ORDER_LIST_PATH = "";
export const ORDER_DETAIL_PATH = ":orderId";
export const ORDER_CHECKOUT_PATH = "checkout";
export const ORDER_LIST_URL = `/${formatPath(ORDER_PAGES_ROOT)}${formatPath(ORDER_LIST_PATH)}`;
export const ORDER_DETAIL_URL = `/${formatPath(ORDER_PAGES_ROOT)}${formatPath(ORDER_DETAIL_PATH)}`;
export const ORDER_CHECKOUT_URL = `/${formatPath(ORDER_PAGES_ROOT)}${formatPath(ORDER_CHECKOUT_PATH)}`;