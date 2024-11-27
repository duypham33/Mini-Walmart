import { ChangeDetectionStrategy, Component } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { delay, exhaustMap, of, Subscription, take, tap } from 'rxjs';
import { LOGIN_URL } from 'src/app/core/constants/app-urls.constant';
import { AuthService } from 'src/app/core/services/auth.service';
import { appStatusLoadingAction, appStatusOkWithInfoAction } from 'src/app/core/store/app-status/app-status.action';
import { AppState } from 'src/app/core/store/app.state';
import { userValidateTokenAction } from 'src/app/core/store/user/user.action';
import { isAuthenticatedSelector } from 'src/app/core/store/user/user.selector';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterComponent {
  focusField: string | null = null;
  LOGIN_URL = LOGIN_URL;

  constructor(private store: Store<AppState>, private router: Router, private authService: AuthService){}
  
  isAuthenticated$ = this.store.select(isAuthenticatedSelector);
  isAuthSub?: Subscription;
  ngOnInit(): void {
    if(this.isAuthSub) return;
    this.isAuthSub = this.isAuthenticated$.subscribe(
      isAuth => {
        // console.log(isAuth);
        if(isAuth === true) this.router.navigateByUrl("/");
        else if(isAuth === undefined) this.store.dispatch(userValidateTokenAction());
      }
    )
  }

  ngOnDestroy(): void {
    if(this.isAuthSub) this.isAuthSub.unsubscribe();
  }

  onFocus(field: string){
    this.focusField = field;
  }

  onBlur(field: string){
    if(this.focusField === field) this.focusField = null;
  }

  onSubmit(registerForm: NgForm){
    of(this.store.dispatch(appStatusLoadingAction())).pipe(
      delay(300),
      exhaustMap(() => this.authService.register({
        username: registerForm.value["username"].trim(),
        email: registerForm.value["email"].trim(),
        password: registerForm.value["password"].trim()
      })),
      tap(() => {
        this.store.dispatch(appStatusOkWithInfoAction({
            info: `Successfully register your account! Let's login to shopping!`
        }));
        this.router.navigateByUrl(LOGIN_URL);
      }),
      take(1)
    ).subscribe();
  }

  isInValid(control: NgModel): boolean{
    return control.touched && control.invalid ? true : false;
  }

  getError(field: string, errors: {[key: string]: any}): string{
    if(errors["required"]) return field + " is required!";
    if(errors["minlength"]) return `${field} must have at least ${errors["minlength"].requiredLength} characters!`;
    return "";
  }
}
