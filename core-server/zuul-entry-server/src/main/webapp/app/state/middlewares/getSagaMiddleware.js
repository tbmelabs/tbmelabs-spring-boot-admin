// @flow

import createSagaMiddleware from 'redux-saga';

const sagaMiddleware = createSagaMiddleware();

export default () => {
  return sagaMiddleware;
}