import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer/footer.component';
import { InputErrorComponent } from './errors/input-error/input-error.component';
import { OverlayMaskComponent } from './overlay-mask/overlay-mask.component';
import { CounterComponent } from './counter/counter.component';
import { CounterTextComponent } from './counter-text/counter-text.component';
import { ErrorNotifyComponent } from './errors/error-notify/error-notify.component';
import { FormsModule } from '@angular/forms';
import { LoaderComponent } from './loader/loader.component';
import { InfoPopupComponent } from './info-popup/info-popup.component';

@NgModule({
  declarations: [
    FooterComponent,
    InputErrorComponent,
    OverlayMaskComponent,
    CounterComponent,
    CounterTextComponent,
    ErrorNotifyComponent,
    LoaderComponent,
    InfoPopupComponent
  ],
  exports: [
    FooterComponent,
    InputErrorComponent,
    OverlayMaskComponent,
    CounterTextComponent,
    ErrorNotifyComponent,
    LoaderComponent,
    InfoPopupComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ]
})
export class ComponentsModule { }
