// @flow

import {takeEvery} from 'redux-saga';

import axios from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {REQUEST_CLIENTS, setClientsAction} from '../../actions/client';

function* requestClients() {
  const response = yield axios.get(`${REST_API_BASE_PATH}/clients`);
  yield put(setClientsAction(response.data));
}

export function* requestClientsSaga() {
  yield takeEvery(REQUEST_CLIENTS, requestClients);
}
