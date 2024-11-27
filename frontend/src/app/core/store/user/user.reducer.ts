import { createReducer, on } from "@ngrx/store";
import { initUserState } from "./user.state";
import { userAuthSuccessAction, userCleanCartAction, userGetCartSuccessAction, userGetWatchListSuccessAction, userLogoutSuccessAction, userUpdateCartSuccessAction, userUpdateWatchListSuccessAction } from "./user.action";

export const userReducer = createReducer(
    initUserState,
    on(
        userAuthSuccessAction, (state, payload) => ({
            user: {
                id: payload.id,
                username: payload.username,
                email: payload.email,
                role: payload.role
            },
            auth: {
                isAuthenticated: true,
                authToken: payload.token
            },
            cart: state.cart,
            watchList: state.watchList
        })
    ),
    on(
        userLogoutSuccessAction, () => ({auth: {isAuthenticated: false}, cart: [], watchList: []})
    ),
    on(userGetCartSuccessAction, (state, payload) => {
        // console.log(payload);
        // console.log(state);
        return {
            ...state,
            cart: payload.items
        }
    }),
    on(userUpdateCartSuccessAction, (state, payload) => {
        let items = [...state.cart];
        if(payload.quantity < 1) items = items.filter(i => i.product.id !== payload.product.id);
        else{
            // Try to keep position order
            // console.log(items);
            const index = items.findIndex(i => i.product.id === payload.product.id);
            // console.log(index);
            if(index < 0) items = [...items, payload];
            else {
                items.splice(index, 1, payload);
            }
            // console.log(items);
        }
        
        return {
            ...state,
            cart: items
        }
    }),
    on(userCleanCartAction, (state, _) => {
        return {
            ...state,
            cart: []
        }
    }),
    on(userGetWatchListSuccessAction, (state, payload) => {
        // console.log(payload);
        // console.log(state);
        return {
            ...state,
            watchList: payload.products.map(p => p.id)
        }
    }),
    on(userUpdateWatchListSuccessAction, (state, payload) => {
        let wl = state.watchList;
        if(!payload.isAdd) wl = wl.filter(w => w !== payload.productId);
        else if(!wl.includes(payload.productId)) wl = [...wl, payload.productId];
        return {
            ...state,
            watchList: wl
        }
    })
);