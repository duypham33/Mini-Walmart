import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { filter, map } from 'rxjs';
import { USER_ROLE } from 'src/app/core/constants/user-role.constant';
import { AppState } from 'src/app/core/store/app.state';
import { currentUserSelector } from 'src/app/core/store/user/user.selector';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent {
  constructor(private store: Store<AppState>){}

  userRole$ = this.store.select(currentUserSelector).pipe(
    filter(user => user !== undefined),
    map(user => user!.role)
  );

  isAdmin(role: string){
    return role === USER_ROLE.ADMIN.toString();
  }
}
