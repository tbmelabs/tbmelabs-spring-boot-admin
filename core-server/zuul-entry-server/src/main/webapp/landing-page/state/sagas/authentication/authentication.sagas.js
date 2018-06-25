// @flow

import {put, takeLatest} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {
  REQUEST_AUTHENTICATION,
  setAuthenticationAction
} from '../../actions/authentication';

function* requestAuthentication() {
  const response: AxiosResponse = yield axios.get('authenticated');
  yield put(setAuthenticationAction(response.data));
}

export function* requestAuthenticationSaga(): Generator<any, void, any> {
  yield takeLatest(REQUEST_AUTHENTICATION, requestAuthentication);
}
