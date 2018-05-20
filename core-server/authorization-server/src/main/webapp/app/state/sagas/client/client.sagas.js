// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {
  REQUEST_CLIENTS,
  SAVE_CLIENT,
  setClientsAction
} from '../../actions/client';

import type {clientType} from '../../../../common/types/client.type';

function* requestClients() {
  const response: AxiosResponse = yield axios.get(
      `${REST_API_BASE_PATH}/clients`);
  if (response.status === 200) {
    yield put(setClientsAction(response.data));
  }
}

export function* requestClientsSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_CLIENTS, requestClients);
}

function* saveClient(action: { type: string, payload: clientType }) {
  const response: AxiosResponse = yield axios.post(
      `${REST_API_BASE_PATH}/clients`, action.payload);

  console.log('got response: ', response);
}

export function* saveClientSaga(): Generator<any, void, any> {
  yield takeEvery(SAVE_CLIENT, saveClient);
}
