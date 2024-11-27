import { createAction, props } from "@ngrx/store";
import { LoginRequest } from "../../models/user/requests/login-request.model";
import { AuthResponse } from "../../models/user/responses/auth-response.model";
import { ICartResponse } from "../../models/user/responses/cart-response.model";
import { IWatchListResponse } from "../../models/user/responses/watchlist-response.model";
import { IUpdateCartRequest } from "../../models/user/requests/update-cart-item.model";
import { ICartItem } from "../../models/user/cart-item.model";

const USER_LOGIN = "[USER] login";
const USER_VALIDATE_TOKEN = "[USER] validate token";

const USER_AUTH_SUCCESS = "[USER] auth success";

const USER_LOGOUT = "[USER] logout";
const USER_LOGOUT_SUCCESS = "[USER] logout success";

export const userLoginAction = createAction(USER_LOGIN, props<LoginRequest>());
export const userValidateTokenAction = createAction(USER_VALIDATE_TOKEN);

export const userAuthSuccessAction = createAction(USER_AUTH_SUCCESS, props<AuthResponse>());

export const userLogoutAction = createAction(USER_LOGOUT, props<{checkingAuth?: boolean}>());
export const userLogoutSuccessAction = createAction(USER_LOGOUT_SUCCESS);


const USER_GET_CART = "[USER] get cart";
export const userGetCartAction = createAction(USER_GET_CART);
const USER_GET_CART_SUCCESS = "[USER] get cart success";
export const userGetCartSuccessAction = createAction(USER_GET_CART_SUCCESS, props<ICartResponse>());
const USER_UPDATE_CART = "[USER] update cart";
export const userUpdateCartAction = createAction(USER_UPDATE_CART, props<IUpdateCartRequest>());
const USER_UPDATE_SUCCESS_CART = "[USER] update cart success";
export const userUpdateCartSuccessAction = createAction(USER_UPDATE_SUCCESS_CART, props<ICartItem>());
const USER_CLEAN_CART = "[USER] clean cart";
export const userCleanCartAction = createAction(USER_CLEAN_CART);


const USER_GET_WATCH_LIST = "[USER] get watchlist";
export const userGetWatchListAction = createAction(USER_GET_WATCH_LIST);
const USER_GET_WATCH_LIST_SUCCESS = "[USER] get watchlist success";
export const userGetWatchListSuccessAction = createAction(USER_GET_WATCH_LIST_SUCCESS, props<IWatchListResponse>());
const USER_UPDATE_WATCH_LIST = "[USER] update WATCH_LIST";
export const userUpdateWatchListAction = createAction(
    USER_UPDATE_WATCH_LIST, props<{productId: number, isAdd: boolean}>()
);
const USER_UPDATE_SUCCESS_WATCH_LIST = "[USER] update WATCH_LIST success";
export const userUpdateWatchListSuccessAction = createAction
(USER_UPDATE_SUCCESS_WATCH_LIST, props<{productId: number, isAdd: boolean}>()
);