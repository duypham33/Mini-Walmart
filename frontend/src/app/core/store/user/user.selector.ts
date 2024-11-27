import { createFeatureSelector, createSelector } from "@ngrx/store";
import { UserState } from "./user.state";

const userStateSelector = createFeatureSelector<UserState>("user");

export const currentUserSelector = createSelector(userStateSelector, state => state.user);
export const isAuthenticatedSelector = createSelector(userStateSelector, state => state.auth.isAuthenticated);
export const userCartSelector = createSelector(userStateSelector, state => state.cart);
export const userWatchListSelector = createSelector(userStateSelector, state => state.watchList);