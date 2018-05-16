// @flow

import {takeEvery} from 'redux-saga';

import axios from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {
  REQUEST_CLIENT_SCOPES,
  setClientScopesAction
} from '../../actions/scope';

function* requestClientScopes() {
  const response = yield axios.get(`${REST_API_BASE_PATH}/scopes`);
  yield put(setClientScopesAction(response.data));
}

export function* requestClientScopesSaga() {
  yield takeEvery(REQUEST_CLIENT_SCOPES, requestClientScopes);
}
