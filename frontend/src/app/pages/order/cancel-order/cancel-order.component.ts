import { ChangeDetectionStrategy, Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-cancel-order',
  templateUrl: './cancel-order.component.html',
  styleUrls: ['./cancel-order.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CancelOrderComponent {
  @Output() cancelECash = new EventEmitter<void>();
  @Output() cancelRefund = new EventEmitter<void>();

  onECash(){
    this.cancelECash.emit();
  }

  onRefund(){
    this.cancelRefund.emit();
  }
}
