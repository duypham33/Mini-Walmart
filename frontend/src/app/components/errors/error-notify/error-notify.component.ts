import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { map } from 'rxjs';
import { appStatusOkAction } from 'src/app/core/store/app-status/app-status.action';
import { appErrorSelector } from 'src/app/core/store/app-status/app-status.selector';
import { AppState } from 'src/app/core/store/app.state';

@Component({
  selector: 'app-error-notify',
  templateUrl: './error-notify.component.html',
  styleUrls: ['./error-notify.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ErrorNotifyComponent {
  constructor(private store: Store<AppState>){}

  error$ = this.store.select(appErrorSelector);

  onClose(){
    this.store.dispatch(appStatusOkAction());
  }
}
