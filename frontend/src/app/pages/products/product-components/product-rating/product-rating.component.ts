import { ChangeDetectionStrategy, Component, Input } from '@angular/core';

@Component({
  selector: 'app-product-rating',
  templateUrl: './product-rating.component.html',
  styleUrls: ['./product-rating.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductRatingComponent {
  @Input() rating!: number;

  get generateArray(): number[] {
    return new Array(Math.floor(this.rating));
  }

  get hasHalf(){
    return Math.floor(this.rating) < this.rating;
  }
}
