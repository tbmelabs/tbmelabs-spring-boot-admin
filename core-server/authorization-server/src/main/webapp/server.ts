// TODO: Install the latest stable version as soon as the pull-request is merged
// --> https://github.com/angular/universal/pull/999
import * as socketEngine from './vendor/socket-engine';
// const socketEngine = require('@nguniversal/socket-engine');

// * NOTE :: leave this as require() since this file is built Dynamically from webpack
const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('./dist/server/main');

socketEngine.startSocketEngine(AppServerModuleNgFactory);
