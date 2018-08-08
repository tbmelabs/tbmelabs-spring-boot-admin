require('zone.js');

import {provideModuleMap} from '@nguniversal/module-map-ngfactory-loader';
import {renderModuleFactory} from '@angular/platform-server';

const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('./dist/server/main');

export declare function registerRenderAdapter(renderadapter: RenderAdapter): void;

export declare function receiveRenderedPage(uuid: string, html: string, error: any): void;

export class RenderAdapter {
  constructor(private appServerModuleNgFactory: any, private lazyModuleMap: any) {
    registerRenderAdapter(this);
  }

  renderPage(uuid: string, uri: string) {
    renderModuleFactory(this.appServerModuleNgFactory, {
      document: '<app-root></app-root>',
      url: uri,
      extraProviders: [
        provideModuleMap(this.lazyModuleMap)
      ]
    }).then(html => {
      receiveRenderedPage(uuid, html, null);
    }).catch(error => {
      receiveRenderedPage(uuid, null, error);
    });
  }
}

// tslint:disable-next-line
new RenderAdapter(AppServerModuleNgFactory, LAZY_MODULE_MAP);
