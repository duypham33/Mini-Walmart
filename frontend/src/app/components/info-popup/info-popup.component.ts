import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { tap } from 'rxjs';
import { appStatusInfoAction } from 'src/app/core/store/app-status/app-status.action';
import { appInfoSelector } from 'src/app/core/store/app-status/app-status.selector';
import { AppState } from 'src/app/core/store/app.state';

@Component({
  selector: 'app-info-popup',
  templateUrl: './info-popup.component.html',
  styleUrls: ['./info-popup.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class InfoPopupComponent {
  constructor(private store: Store<AppState>){}

  info$ = this.store.select(appInfoSelector).pipe(
    tap(info => {
      if(info === undefined) return;
      console.log("Set timer");
      setTimeout(() => {
        this.store.dispatch(appStatusInfoAction({}));
        console.log("Removed info");
      }, 8000);
    })
  );
}
