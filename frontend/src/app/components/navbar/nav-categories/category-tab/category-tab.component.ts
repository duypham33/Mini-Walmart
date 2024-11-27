import { ChangeDetectionStrategy, Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-category-tab',
  templateUrl: './category-tab.component.html',
  styleUrls: ['./category-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CategoryTabComponent implements OnChanges{
  @Input() iconUrl!: string;
  url: string | null = null;
  @Input() text!: string;
  @Input() isSelected: boolean = false;

  ngOnChanges(changes: SimpleChanges): void {
    this.url = changes["iconUrl"].currentValue;
  }

  get getIconUrl(){
    return this.url || this.iconUrl;
  }

  onClick(){
    this.isSelected = !this.isSelected;
    if(!this.isSelected) {
      this.url = this.iconUrl;
      return;
    }
    
    const tokens = this.iconUrl.split("/");
    const filename = tokens[tokens.length - 1];
    tokens[tokens.length - 1] = "active_" + filename;
    this.url = tokens.join("/");
  }
}
