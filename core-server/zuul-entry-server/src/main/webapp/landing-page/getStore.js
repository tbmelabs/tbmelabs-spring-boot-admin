// @flow
'use strict';

import {applyMiddleware, compose, createStore} from 'redux';
import thunk from 'redux-thunk';

import reducers from './state/reducers';

import getLoggerMiddleware from './state/middlewares/getLoggerMiddleware';
import getSagaMiddleware from './state/middlewares/getSagaMiddleware';

import sagas from './state/sagas';

const middlewares = [];
const environment = process.env.NODE_ENV;

middlewares.push(thunk);

if (environment === 'development') {
  middlewares.push(getLoggerMiddleware());
}

middlewares.push(getSagaMiddleware());

const store = createStore(
    reducers,
    compose(
        applyMiddleware(...middlewares),
        window.devToolsExtension ? window.devToolsExtension() : f => f;
    )
)
sagas.forEach(saga = > getSagaMiddleware().run(saga);
)
export default () => {
  return store;
}
