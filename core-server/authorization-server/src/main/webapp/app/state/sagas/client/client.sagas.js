// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {REQUEST_CLIENTS, setClientsAction} from '../../actions/client';

function* requestClients() {
  const response: AxiosResponse = yield axios.get(
      `${REST_API_BASE_PATH}/clients`);
  yield put(setClientsAction(response.data));
}

export function* requestClientsSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_CLIENTS, requestClients);
}
