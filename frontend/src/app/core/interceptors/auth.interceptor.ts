import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError, Observable, of } from 'rxjs';
import { getAuthToken } from '../utils/user.util';
import { Store } from '@ngrx/store';
import { appStatusErrorAction } from '../store/app-status/app-status.action';
import { parseResponseErr } from '../utils/request.util';
import { Router } from '@angular/router';
import { LOGIN_URL } from '../constants/app-urls.constant';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private store: Store, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    request = request.clone({
      headers: request.headers.set(
        'authorization', `Bearer ${getAuthToken()}`
      )
    });

    return next.handle(request).pipe(
        catchError((error: HttpErrorResponse) => {
            console.log(error);
            if(!error.error){
              this.router.navigateByUrl(LOGIN_URL);
              return of();
            }
            this.store.dispatch(appStatusErrorAction({
                error: {
                    message: parseResponseErr(error),
                    code: error.status,
                    status: error.statusText
                }
            }))
            return of();
        })
    );
  }
}

export const AuthInterceptorProvider = {
  provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true
}