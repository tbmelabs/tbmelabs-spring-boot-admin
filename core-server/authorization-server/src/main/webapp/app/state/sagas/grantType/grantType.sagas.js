// @flow

import {takeEvery} from 'redux-saga';

import axios from 'axios';

import {REST_API_BASE_PATH} from '../../../config';

import {
  REQUEST_CLIENT_GRANT_TYPES,
  setClientGrantTypesAction
} from '../../actions/grantType';

function* requestClientGrantTypes() {
  const response = yield axios.get(`${REST_API_BASE_PATH}/grant-types`);
  yield put(setClientGrantTypesAction(response.data));
}

export function* requestClientGrantTypesSaga() {
  yield takeEvery(REQUEST_CLIENT_GRANT_TYPES, requestClientGrantTypes);
}
