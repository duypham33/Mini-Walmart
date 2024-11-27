import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { LOGIN_URL, ORDER_LIST_URL, PRODUCTS_LIST_URL } from 'src/app/core/constants/app-urls.constant';
import { USER_ROLE } from 'src/app/core/constants/user-role.constant';
import { User } from 'src/app/core/models/user/user.model';
import { AppState } from 'src/app/core/store/app.state';
import { userLogoutAction } from 'src/app/core/store/user/user.action';
import { currentUserSelector } from 'src/app/core/store/user/user.selector';

@Component({
  selector: 'app-account-nav-tab',
  templateUrl: './account-nav-tab.component.html',
  styleUrls: ['./account-nav-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccountNavTabComponent {
  constructor(private router: Router, private store: Store<AppState>, private chRef: ChangeDetectorRef){}
  user$ = this.store.select(currentUserSelector);

  getUserName(user: User){
    return `Hi, ${user.username}`;
  }

  isAdmin(user: User){
    return user.role === USER_ROLE.ADMIN.toString();
  }

  isSelected: boolean = false;
  onClick(){
    this.isSelected = !this.isSelected;
  }

  navigateProducts(){
    this.router.navigateByUrl(PRODUCTS_LIST_URL);
    this.isSelected = false;
  }
  
  openProduct: boolean = false;
  openNewProduct(){
    this.openProduct = true;
    this.isSelected = false;
  }

  closeNewProduct(){
    console.log("created");
    this.openProduct = false;
    this.isSelected = false;
    this.chRef.detectChanges();
  }

  navigateOrders(){
    this.router.navigateByUrl(ORDER_LIST_URL);
    this.isSelected = false;
  }

  logout(){
    this.store.dispatch(userLogoutAction({}));
    this.router.navigateByUrl(LOGIN_URL);
    this.isSelected = false;
  }
}
