// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {
  REQUEST_CLIENT_AUTHORITIES,
  setClientAuthoritiesAction
} from '../../actions/authority';

function* requestClientAuthorities() {
  const response: AxiosResponse = yield axios.get(
      `${REST_API_BASE_PATH}/authorities`);
  yield put(setClientAuthoritiesAction(response.data));
}

export function* requestClientAuthoritiesSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_CLIENT_AUTHORITIES, requestClientAuthorities);
}
