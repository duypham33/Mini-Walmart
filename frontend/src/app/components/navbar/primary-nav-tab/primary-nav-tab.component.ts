import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-primary-nav-tab',
  templateUrl: './primary-nav-tab.component.html',
  styleUrls: ['./primary-nav-tab.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PrimaryNavTabComponent {
  @Input() iconUrl!: string;
  @Input() title!: string;
  @Input() content!: string;
  @Input() isReverse: boolean | null = null;
  @Input() isDarkBackground: boolean | null = null;

  isSelected: boolean = false;
  
  @Output() click = new EventEmitter<void>();
  onClick(){
    // this.isSelected = !this.isSelected;
    this.click.emit();
  }

  get classForColor(){
    const c1 = this.isDarkBackground ? 'dark' : '';
    const c2 = this.isSelected ? 'selected' : '';
    return `${c1} ${c2}`;
  }
}
