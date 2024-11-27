import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-product-action-button',
  templateUrl: './product-action-button.component.html',
  styleUrls: ['./product-action-button.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductActionButtonComponent {
  @Input() text?: string;
  @Input() items: number = 0;
  @Input() customStyles?: {[pros: string]: any};
  @Output() onUpdate = new EventEmitter<number>();

  add(){
    this.onUpdate.emit(1);
  }

  sub(){
    this.onUpdate.emit(-1);
  }
}
