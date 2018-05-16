// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {
  REQUEST_CLIENT_SCOPES,
  setClientScopesAction
} from '../../actions/scope';

function* requestClientScopes() {
  const response: AxiosResponse = yield axios.get(
      `${REST_API_BASE_PATH}/scopes`);
  yield put(setClientScopesAction(response.data));
}

export function* requestClientScopesSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_CLIENT_SCOPES, requestClientScopes);
}
