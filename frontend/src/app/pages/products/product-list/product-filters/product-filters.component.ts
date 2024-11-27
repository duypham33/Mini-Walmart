import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { IProductFilter } from 'src/app/core/models/filter/product-filter.model';
import { IBrand } from 'src/app/core/models/product/brand.model';

@Component({
  selector: 'app-product-filters',
  templateUrl: './product-filters.component.html',
  styleUrls: ['./product-filters.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductFiltersComponent {
  @Input() productFilter: IProductFilter = {brands: []};

  get selectedPrice(){
    return this.productFilter.maxPrice ? "$" + this.productFilter.maxPrice : "No limit";
  }

  @Input() brands!: IBrand[];
  @Input() priceRage!: {
    min: number,
    max: number,
    step: number
  }

  @Output() filter = new EventEmitter<void>();

  get middlePrice(){
    return Math.floor((this.priceRage.max + this.priceRage.min) / 2);
  }

  onFilter(){
    this.productFilter.brands.forEach(b => {
      const brand = this.brands.find(br => br.id === b.id);
      if(brand) b.name = brand.name;
    });
    this.filter.emit();
  }

  isBrandChecked(brandId: number){
    return this.productFilter.brands.findIndex(b => b.id === brandId) >= 0;
  }

  onCheckboxChange(optionId: number, event: any): void {
    if (event.target.checked) 
      this.productFilter.brands.push({id: optionId, name: this.brands.find(b => b.id === optionId)?.name});
    else
      this.productFilter.brands = this.productFilter.brands.filter(b => b.id !== optionId);

    // console.log('Selected brands:', this.productFilter.brands);
  }
}
