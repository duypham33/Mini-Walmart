import { ChangeDetectionStrategy, Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { Subscription } from 'rxjs';
import { REGISTER_URL } from 'src/app/core/constants/app-urls.constant';
import { AppState } from 'src/app/core/store/app.state';
import { userLoginAction, userValidateTokenAction } from 'src/app/core/store/user/user.action';
import { isAuthenticatedSelector } from 'src/app/core/store/user/user.selector';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent implements OnInit, OnDestroy {
  focusField: string | null = null;
  REGISTER_URL = REGISTER_URL;

  constructor(private store: Store<AppState>, private router: Router){}
  
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

  onSubmit(loginForm: NgForm){
    // console.log(loginForm.value["username"]);
    this.store.dispatch(userLoginAction({
      username: loginForm.value["username"].trim(),
      password: loginForm.value["password"].trim()
    }));
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
