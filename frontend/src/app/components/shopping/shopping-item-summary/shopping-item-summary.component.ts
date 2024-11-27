import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { PRODUCTS_DETAIL_URL } from 'src/app/core/constants/app-urls.constant';
import { DEFAULT_PRODUCT_ICON_PATH } from 'src/app/core/constants/icon_paths.constant';
import { IShoppingItem } from 'src/app/core/models/user/shopping-item.model';

@Component({
  selector: 'app-shopping-item-summary',
  templateUrl: './shopping-item-summary.component.html',
  styleUrls: ['./shopping-item-summary.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ShoppingItemSummaryComponent {
  @Input() item!: IShoppingItem;
  
  constructor(private router: Router){}

  get imgPath(){
    return this.item.product.imgPath ?? DEFAULT_PRODUCT_ICON_PATH;
  }

  viewProductDetail(){
    this.router.navigateByUrl(PRODUCTS_DETAIL_URL.replace(":productId", this.item.product.id.toString()));
  }
}
