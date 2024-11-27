import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { StoreModule } from '@ngrx/store';
import { appStatusRedducer } from './store/app-status/app-status.reducer';
import { userReducer } from './store/user/user.reducer';
import { EffectsModule } from '@ngrx/effects';
import { UserEffect } from './store/user/user.effect';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
    StoreModule.forFeature("appStatus", appStatusRedducer),
    StoreModule.forFeature("user", userReducer),
    EffectsModule.forFeature([UserEffect])
  ]
})
export class CoreModule { }
