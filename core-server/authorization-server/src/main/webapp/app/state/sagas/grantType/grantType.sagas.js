// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {
  REQUEST_CLIENT_GRANT_TYPES,
  setClientGrantTypesAction
} from '../../actions/grantType';

function* requestClientGrantTypes() {
  const response: AxiosResponse = yield axios.get(
      `${REST_API_BASE_PATH}/grant-types`);
  yield put(setClientGrantTypesAction(response.data));
}

export function* requestClientGrantTypesSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_CLIENT_GRANT_TYPES, requestClientGrantTypes);
}
