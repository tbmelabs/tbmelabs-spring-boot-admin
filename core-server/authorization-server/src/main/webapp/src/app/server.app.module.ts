import {BrowserModule} from '@angular/platform-browser';
import {ServerModule} from '@angular/platform-server';
import {NgModule} from '@angular/core';

import {AppModule} from './app.module';
import {AppComponent} from './app.component';

@NgModule({
  imports: [
    AppModule,
    BrowserModule.withServerTransition({appId: 'tbme-labs-authorization-server'}),
    ServerModule,
  ],
  bootstrap: [AppComponent]
})
export class ServerAppModule {
}
