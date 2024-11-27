import { ChangeDetectionStrategy, Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-overlay-mask',
  templateUrl: './overlay-mask.component.html',
  styleUrls: ['./overlay-mask.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OverlayMaskComponent {
  @Output() onClose = new EventEmitter<void>();

  onClick(event: Event){
    if((event.target as HTMLElement).className === 'overlay-mask'){
      // console.log("Close");
      this.onClose.emit();
    }
  }
}
