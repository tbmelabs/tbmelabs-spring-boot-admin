// @flow
'use strict';

import {put, takeEvery, takeLatest} from 'redux-saga/effects';

import axios from 'axios';

import {REQUEST_AUTHENTICATION, SetAuthenticationAction, SIGNIN_USER, SIGNOUT_USER} from "../actions/authentication";

export function* requestAuthenticationSaga() {
  yield takeLatest(REQUEST_AUTHENTICATION, () => {
    axios.get('authenticated').then(
      function* (response) {
        yield put(new SetAuthenticationAction(response.data));
      }
      // TODO: new BackendNotReachableAction(error.response.data.message)
    );
  });
}

export function* signinUserSaga() {
  yield takeEvery(SIGNIN_USER, () => window.location.replace('/login'));
}

export function* signoutUserSaga() {
  yield takeEvery(SIGNOUT_USER, () => window.location.replace('/logout'));
}