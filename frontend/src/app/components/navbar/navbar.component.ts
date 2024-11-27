import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { HOME_USER_PATH } from 'src/app/core/constants/app-urls.constant';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavbarComponent {
  constructor(private router: Router){}

  navigateToHome(){
    this.router.navigateByUrl(HOME_USER_PATH);
  }
}
