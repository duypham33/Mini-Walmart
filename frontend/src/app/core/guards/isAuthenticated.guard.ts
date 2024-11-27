import { CanActivateChildFn, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { AppState } from "../store/app.state";
import { Store } from "@ngrx/store";
import { inject } from "@angular/core";
import { isAuthenticatedSelector } from "../store/user/user.selector";
import { catchError, exhaustMap, map, of, take } from "rxjs";
import { appStatusLoadingAction, appStatusOkAction } from "../store/app-status/app-status.action";
import { userAuthSuccessAction } from "../store/user/user.action";
import { LOGIN_URL } from "../constants/app-urls.constant";

export const isAuthenticatedGuard: CanActivateChildFn = () => {
    const authSerivce = inject(AuthService);
    const store = inject(Store<AppState>);
    const router = inject(Router);

    return store.select(isAuthenticatedSelector).pipe(
        exhaustMap(isAuth => {
            if(isAuth !== undefined) return of(isAuth);

            store.dispatch(appStatusLoadingAction());
            return authSerivce.checkAuth().pipe(
                map(res => {
                    store.dispatch(userAuthSuccessAction(res));
                    store.dispatch(appStatusOkAction());
                    return true;
                }),
                catchError(_ => of(false))
            );
        }),
        take(1),
        map(isAuth => isAuth ? true : router.createUrlTree([LOGIN_URL]))
    );
}