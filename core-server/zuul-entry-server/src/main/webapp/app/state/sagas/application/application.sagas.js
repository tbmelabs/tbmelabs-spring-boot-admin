// @flow

import {takeLatest} from 'redux-saga/effects';

import {LAUNCH_APPLICATION} from '../../actions/application/application.types';

function* launchApplication(action: { type: string }) {
  window.location.replace('desktop');
}

export function* launchApplicationSaga() {
  yield takeLatest(LAUNCH_APPLICATION, launchApplication);
}
