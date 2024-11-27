import { ChangeDetectionStrategy, Component, Input } from '@angular/core';

@Component({
  selector: 'app-product-prices',
  templateUrl: './product-prices.component.html',
  styleUrls: ['./product-prices.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductPricesComponent {
  @Input() retailPrice!: number;
  @Input() wholesalePrice: number | undefined;
  @Input() quantity: number | undefined;

  get retailEvenPrice(){
    return Math.floor(this.retailPrice);
  }

  get retailOddPrice(){
    return Math.ceil((this.retailPrice - Math.floor(this.retailPrice)) * 100);
  }
}
