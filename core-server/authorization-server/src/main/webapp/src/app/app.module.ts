import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {SigninComponent} from './signin';
import {SignupComponent} from './signup';
import {ConfirmAccessComponent} from './oauth/confirm-access';
import {AppRoutingModule} from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent,
    SigninComponent,
    SignupComponent,
    ConfirmAccessComponent
  ],
  imports: [
    BrowserModule.withServerTransition({appId: 'tbme-labs-authorization-server'}),
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
