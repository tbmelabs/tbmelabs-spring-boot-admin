// @flow

import {takeEvery} from 'redux-saga';

import axios from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {
  REQUEST_CLIENT_AUTHORITIES,
  setClientAuthoritiesAction
} from '../../actions/authority';

function* requestClientAuthorities() {
  const response = yield axios.get(`${REST_API_BASE_PATH}/authorities`);
  yield put(setClientAuthoritiesAction(response.data));
}

export function* requestClientAuthoritiesSaga() {
  yield takeEvery(REQUEST_CLIENT_AUTHORITIES, requestClientAuthorities);
}
