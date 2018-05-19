// @flow

import {takeEvery} from 'redux-saga/effects';

import {SIGNOUT_USER} from '../../actions/authentication';

function* signoutUser() {
  window.location.replace('logout');
}

export function* signoutUserSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNOUT_USER, signoutUser);
}
