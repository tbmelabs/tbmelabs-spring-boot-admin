// @flow
'use strict';

import {applyMiddleware, compose, createStore} from 'redux';
import thunk from 'redux-thunk';

import reducers from './reducers';

import createSagaMiddleware from 'redux-saga';
import sagas from './sagas';

const sagaMiddleware = createSagaMiddleware();

export default () => {
  return createStore(
    reducers,
    compose(
      applyMiddleware(thunk, sagaMiddleware),
      window.devToolsExtension ? window.devToolsExtension() : f => f
    )
  );
}

sagas.forEach((saga: func) => sagaMiddleware.run(saga));