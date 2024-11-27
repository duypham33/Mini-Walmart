import { ChangeDetectionStrategy, ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { PRODUCTS_LIST_URL } from 'src/app/core/constants/app-urls.constant';
import { ICategory, ICategoryFamily } from 'src/app/core/models/product/category.model';
import { ProductService } from 'src/app/core/services/product.service';

@Component({
  selector: 'app-nav-categories',
  templateUrl: './nav-categories.component.html',
  styleUrls: ['./nav-categories.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavCategoriesComponent {
  bookmarks: string[] = ["Savings", "Thanksgiving", "Holiday Shop", 
    "Gift Ideas", "New & Trending", "Toy Shop", "Home"];
  
  constructor(
    private productService: ProductService, private chRef: ChangeDetectorRef, private router: Router
  ){}
  rootCategories: ICategory[] = [];
  categoryFamilies: ICategoryFamily[] = [];

  openCategory: boolean = false;
  toggleOpenCategory(){
    this.openCategory = !this.openCategory;
    if(this.openCategory || this.rootCategories.length === 0){
      this.productService.getRootCategories().subscribe(
        cs => {
          this.rootCategories = cs;
          this.chRef.detectChanges();
        }
      );
    }
    else{
      this.selectedRootId = -1;
      this.categoryFamilies = [];
    }
  }
  
  selectedRootId: number = -1;
  selectRoot(rootId: number){
    if(this.selectedRootId === rootId) return;
    this.selectedRootId = rootId;
    this.productService.getCategoryFamilies(rootId).subscribe(
      cs => {
        this.categoryFamilies = cs;
        this.chRef.detectChanges();
      }
    );
  }

  onNavigate(categoryId: number){
    this.toggleOpenCategory();
    this.router.navigate([PRODUCTS_LIST_URL], {
      queryParams: {categoryId}
    });
  }
}
