import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { Store } from "@ngrx/store";
import { AuthService } from "../../services/auth.service";
import { userAuthSuccessAction, userGetCartAction, userGetCartSuccessAction, userGetWatchListAction, userGetWatchListSuccessAction, userLoginAction, userLogoutAction, userLogoutSuccessAction, userUpdateCartAction, userUpdateCartSuccessAction, userUpdateWatchListAction, userUpdateWatchListSuccessAction, userValidateTokenAction } from "./user.action";
import { catchError, delay, EMPTY, exhaustMap, map, of, tap } from "rxjs";
import { appStatusErrorAction, appStatusLoadingAction, appStatusOkAction, appStatusOkWithInfoAction } from "../app-status/app-status.action";
import { UserService } from "../../services/user.service";

@Injectable()
export class UserEffect{
    constructor(
        private store: Store,
        private actions: Actions,
        private authService: AuthService,
        private userService: UserService
    ){}

    _catchError(message: string){
        console.log(message);
        this.store.dispatch(appStatusErrorAction({error: {message}}));
        return EMPTY;
    }

    _login$ = createEffect(() => this.actions.pipe(
        ofType(userLoginAction),
        tap(() => this.store.dispatch(appStatusLoadingAction())),
        delay(500),
        exhaustMap(action => {
            return this.authService.login(action).pipe(
                map(auth => {
                    this.store.dispatch(appStatusOkWithInfoAction({
                        info: `Welcome back ${auth.username}! Let's shopping now!`
                    }));
                    return userAuthSuccessAction(auth);
                }),
                // exhaustMap(() => this.userService.getCart().pipe(
                //     map(cart => userGetCartSuccessAction(cart))
                // )),
                catchError((error: Error) => this._catchError(error.message))
            )
        })
    ));

    _checkAuth$ = createEffect(() => this.actions.pipe(
        ofType(userValidateTokenAction),
        tap(() => this.store.dispatch(appStatusLoadingAction())),
        delay(100),
        exhaustMap(action => {
            return this.authService.checkAuth().pipe(
                map(auth => {
                    this.store.dispatch(appStatusOkAction());
                    return userAuthSuccessAction(auth);
                }),
                catchError((error: Error) => of(userLogoutAction({checkingAuth: true})))
            )
        })
    ));

    _logout$ = createEffect(() => this.actions.pipe(
        ofType(userLogoutAction),
        tap(() => this.store.dispatch(appStatusLoadingAction())),
        exhaustMap(action => {
            return this.authService.logout().pipe(
                map(() => {
                    if(action.checkingAuth){
                        this.store.dispatch(appStatusOkAction());
                    }
                    else{
                        this.store.dispatch(appStatusOkWithInfoAction({
                            info: `Successfully Logout!`
                        }));
                    }
                    return userLogoutSuccessAction();
                }),
                catchError((error: Error) => this._catchError(error.message))
            );
        })
    ));

    _getCart$ = createEffect(() => this.actions.pipe(
        ofType(userGetCartAction),
        // tap(() => this.store.dispatch(appStatusLoadingAction())),
        exhaustMap(action => {
            return this.userService.getCart().pipe(
                map(cart => userGetCartSuccessAction(cart))
            )
        })
    ));

    _updateCart$ = createEffect(() => this.actions.pipe(
        ofType(userUpdateCartAction),
        tap(() => this.store.dispatch(appStatusLoadingAction())),
        delay(300),
        exhaustMap(action => {
            return this.userService.updateCart(action).pipe(
                map((item) => {
                    this.store.dispatch(appStatusOkAction());
                    return userUpdateCartSuccessAction(item);
                })
            )
        })
    ));

    _getWatchList$ = createEffect(() => this.actions.pipe(
        ofType(userGetWatchListAction),
        // tap(() => this.store.dispatch(appStatusLoadingAction())),
        exhaustMap(action => {
            return this.userService.getWatchList().pipe(
                map(products => userGetWatchListSuccessAction({products}))
            )
        })
    ));

    _updateWatchList$ = createEffect(() => this.actions.pipe(
        ofType(userUpdateWatchListAction),
        tap(() => this.store.dispatch(appStatusLoadingAction())),
        delay(300),
        exhaustMap(action => {
            return this.userService.updateWatchList(action).pipe(
                map(() => {
                    this.store.dispatch(appStatusOkAction());
                    return userUpdateWatchListSuccessAction(action);
                })
            )
        })
    ));
}