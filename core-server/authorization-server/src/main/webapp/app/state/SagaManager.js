// @flow

import shortid from 'shortid';

import {cancel, takeEvery} from 'redux-saga/effects';

import getSagaMiddleware from './middlewares/getSagaMiddleware';

const tasks: { [key: string]: () => void } = {};

export function getTasks() {
  return tasks;
}

export function addSaga(actionType: string,
    saga: () => void): string {
  const id = shortid.generate();

  tasks[id] = getSagaMiddleware().run(function* () {
    yield takeEvery(actionType, saga);
  });

  return id;
}

export function removeSaga(id: string): void {
  getSagaMiddleware().run(function* () {
    if (tasks[id]) {
      yield cancel(tasks[id]);
      delete tasks[id];
    }
  });
}
