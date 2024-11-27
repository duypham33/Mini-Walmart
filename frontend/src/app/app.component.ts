import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { AppState } from './core/store/app.state';
import { Store } from '@ngrx/store';
import { isAuthenticatedSelector } from './core/store/user/user.selector';
import { Subscription } from 'rxjs';
import { userGetCartAction, userGetWatchListAction } from './core/store/user/user.action';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  isInAuthPages: boolean = true;

  constructor(private location: Location, private store: Store<AppState>){}

  ngOnInit(): void {
      this.location.onUrlChange(url => {
        // console.log(url);
        const isInAuthPages = url.startsWith("/auth");
        if(isInAuthPages === this.isInAuthPages) return;
        this.isInAuthPages = isInAuthPages;
      });

      this.fetchUserData();
  }

  isAuthenticated$ = this.store.select(isAuthenticatedSelector);
  isAuthSub?: Subscription;
  fetchUserData(): void {
    if(this.isAuthSub) return;
    this.isAuthSub = this.isAuthenticated$.subscribe(
      isAuth => {
        if(isAuth === true) {
          this.store.dispatch(userGetCartAction());
          this.store.dispatch(userGetWatchListAction());
        }
      }
    )
  }

  ngOnDestroy(): void {
    if(this.isAuthSub) this.isAuthSub.unsubscribe();
  }
}
