// @flow

import {takeEvery} from 'redux-saga/effects';

import {SIGNIN_USER} from '../../actions/authentication';

export function* signinUserSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNIN_USER, () => window.location.replace('/login'));
}
