import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { combineLatest, map } from 'rxjs';
import { UserService } from 'src/app/core/services/user.service';
import { AppState } from 'src/app/core/store/app.state';
import { userWatchListSelector } from 'src/app/core/store/user/user.selector';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WatchlistComponent {
  constructor(
    private userService: UserService,
    private store: Store<AppState>
  ){}
  
  watchList$ = this.store.select(userWatchListSelector);
  products$ = combineLatest([this.userService.getWatchList(), this.watchList$]).pipe(
    map(([products, ids]) => products.filter(p => ids.includes(p.id)))
  );
}
