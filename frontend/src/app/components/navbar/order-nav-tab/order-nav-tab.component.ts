import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { WATCHLIST_PATH_URL } from 'src/app/core/constants/app-urls.constant';

@Component({
  selector: 'app-order-nav-tab',
  templateUrl: './order-nav-tab.component.html',
  styleUrls: ['./order-nav-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrderNavTabComponent {
  constructor(private router: Router){}

  toWatchList(){
    this.router.navigateByUrl(WATCHLIST_PATH_URL);
  }
}
