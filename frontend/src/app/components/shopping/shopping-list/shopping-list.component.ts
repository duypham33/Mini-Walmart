import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { ORDER_CHECKOUT_URL } from 'src/app/core/constants/app-urls.constant';
import { IShoppingItem } from 'src/app/core/models/user/shopping-item.model';
import { getTotalItems } from 'src/app/core/utils/shopping-item.util';

@Component({
  selector: 'app-shopping-list',
  templateUrl: './shopping-list.component.html',
  styleUrls: ['./shopping-list.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ShoppingListComponent {
  @Input() items!: IShoppingItem[];
  @Input() inCart: boolean = false;
  @Input() isDetail: boolean = false;
  @Input() noActions: boolean = false;

  constructor(private router: Router){}

  toggleDetail(){
    this.isDetail = !this.isDetail;
  }

  isLastItem(index: number){
    return index === this.items.length - 1;
  }

  get numItems(){
    return getTotalItems(this.items);
  }

  get canCheckout(){
    return this.inCart && this.items.length > 0;
  }

  goCheckout(){
    this.router.navigateByUrl(ORDER_CHECKOUT_URL);
  }
}
