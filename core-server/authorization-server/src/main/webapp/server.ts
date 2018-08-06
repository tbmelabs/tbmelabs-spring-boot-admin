require('zone.js');

// TODO: Install the latest stable version as soon as the pull-request is merged
// --> https://github.com/angular/universal/pull/999
import * as socketEngine from './vendor/socket-engine';
import {SocketEngineServer} from './vendor/socket-engine/src/main';
// const socketEngine = require('@nguniversal/socket-engine');

// * NOTE :: leave this as require() since this file is built Dynamically from webpack
const {AppServerModuleNgFactory, LAZY_MODULE_MAP} = require('./dist/server/main');

socketEngine.startSocketEngine(AppServerModuleNgFactory).then((server: SocketEngineServer) => {
  console.log('Successfully started socket-engine on port 9090.');
}).catch((error) => {
  throw new Error('Unable to start the socket-engine: ' + error);
});
