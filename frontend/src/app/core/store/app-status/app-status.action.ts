import { createAction, props } from "@ngrx/store";

const APP_LOADING = "[APP STATUS] loading";
const APP_OK = "[APP STATUS] ok";
const APP_OK_WITH_INFO = "[APP STATUS] ok with information";
const APP_INFO = "[APP STATUS] information";
const APP_ERROR = "[APP STATUS] error";

export const appStatusLoadingAction = createAction(APP_LOADING);
export const appStatusOkAction = createAction(APP_OK);
export const appStatusOkWithInfoAction = createAction(APP_OK_WITH_INFO, props<{info: string}>());
export const appStatusInfoAction = createAction(APP_INFO, props<{info?: string}>());
export const appStatusErrorAction = createAction(
    APP_ERROR, props<{error: {message: string, code?: number, status?: string}}>()
);