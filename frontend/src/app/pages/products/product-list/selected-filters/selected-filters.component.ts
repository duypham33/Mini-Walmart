import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { IProductFilter } from 'src/app/core/models/filter/product-filter.model';

@Component({
  selector: 'app-selected-filters',
  templateUrl: './selected-filters.component.html',
  styleUrls: ['./selected-filters.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectedFiltersComponent {
  @Input() productFilter!: IProductFilter;

  get hasFilter(){
    return this.productFilter.maxPrice || this.productFilter.brands.length > 0;
  }

  get getFilterPrice(){
    return "$0 - $" + this.productFilter.maxPrice;
  }
  
  @Output() onRemoveMaxPrice = new EventEmitter<void>();
  removeMaxPrice(){
    this.onRemoveMaxPrice.emit();
  }
  
  @Output() onRemoveBrand = new EventEmitter<number>();
  removeBrand(brandId: number){
    this.onRemoveBrand.emit(brandId);
  }
  
  @Output() onClearAllFilter = new EventEmitter<void>();
  clearAllFilter(){
    this.onClearAllFilter.emit();
  }
}
