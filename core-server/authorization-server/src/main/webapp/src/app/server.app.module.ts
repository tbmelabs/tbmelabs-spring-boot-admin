import {ServerModule} from '@angular/platform-server';
import {NgModule} from '@angular/core';
import {ModuleMapLoaderModule} from '@nguniversal/module-map-ngfactory-loader';

import {AppModule} from './app.module';
import {AppComponent} from './app.component';

@NgModule({
  imports: [
    AppModule,
    ServerModule,
    ModuleMapLoaderModule
  ],
  bootstrap: [AppComponent]
})
export class ServerAppModule {
}
