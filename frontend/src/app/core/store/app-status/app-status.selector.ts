import { createFeatureSelector, createSelector } from "@ngrx/store";
import { AppStatusState } from "./app-status.state";
import { APP_STATUS } from "../../constants/app-status.constants";

const appStatusFeatureSelector = createFeatureSelector<AppStatusState>("appStatus");

export const appStatusSelector = createSelector(appStatusFeatureSelector, state => state.status);
export const appIsLoadingSelector = createSelector(appStatusFeatureSelector, state => state.status === APP_STATUS.LOADING);
export const appInfoSelector = createSelector(appStatusFeatureSelector, state => state.info);
export const appErrorSelector = createSelector(appStatusFeatureSelector, state => state.error);