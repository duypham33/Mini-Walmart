import { ChangeDetectionStrategy, Component } from '@angular/core';
import { Store } from '@ngrx/store';
import { appIsLoadingSelector } from 'src/app/core/store/app-status/app-status.selector';
import { AppState } from 'src/app/core/store/app.state';

@Component({
  selector: 'app-loader',
  templateUrl: './loader.component.html',
  styleUrls: ['./loader.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoaderComponent {
  constructor(private store: Store<AppState>){}

  isLoading$ = this.store.select(appIsLoadingSelector);
}
