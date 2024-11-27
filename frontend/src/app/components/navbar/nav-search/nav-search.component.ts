import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Router } from '@angular/router';
import { PRODUCTS_LIST_URL } from 'src/app/core/constants/app-urls.constant';

@Component({
  selector: 'app-nav-search',
  templateUrl: './nav-search.component.html',
  styleUrls: ['./nav-search.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavSearchComponent {
  isFocus: boolean = false;
  search?: string;

  constructor(private router: Router){}

  onFocus(){
    this.isFocus = true;
  }

  onBlur(){
    this.isFocus = false;
  }

  onSearch(){
    const search = this.search?.trim();
    if(!search) return;
    this.router.navigate([PRODUCTS_LIST_URL], {
      queryParams: {search}
    });
  }
}
