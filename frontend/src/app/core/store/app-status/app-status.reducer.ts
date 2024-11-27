import { createReducer, on } from "@ngrx/store";
import { initAppStatusState } from "./app-status.state";
import { appStatusErrorAction, appStatusInfoAction, appStatusLoadingAction, appStatusOkAction, appStatusOkWithInfoAction } from "./app-status.action";
import { APP_STATUS } from "../../constants/app-status.constants";

export const appStatusRedducer = createReducer(
    initAppStatusState, 
    on(
        appStatusLoadingAction, (_) => ({status: APP_STATUS.LOADING})
    ),
    on(
        appStatusOkAction, (_) => ({status: APP_STATUS.OK})
    ),
    on(
        appStatusOkWithInfoAction, (_, payload) => ({status: APP_STATUS.OK, info: payload.info})
    ),
    on(
        appStatusInfoAction, (state, payload) => ({...state, info: payload.info})
    ),
    on(
        appStatusErrorAction, (_, payload) => ({
            status: APP_STATUS.ERROR, error: payload.error
        })
    )
);