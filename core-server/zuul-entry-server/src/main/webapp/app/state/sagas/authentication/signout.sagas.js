// @flow

import {takeEvery} from 'redux-saga/effects';

import {SIGNOUT_USER} from '../../actions/authentication';

export function* signoutUserSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNOUT_USER, () => window.location.replace('/logout'));
}
