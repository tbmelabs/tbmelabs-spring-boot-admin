// @flow

import {takeEvery} from 'redux-saga/effects';

import {SIGNIN_USER} from '../../actions/authentication';

function* signinUser(): Generator<any, void, any> {
  window.location.replace('/login');
}

export function* signinUserSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNIN_USER, signinUser);
}
