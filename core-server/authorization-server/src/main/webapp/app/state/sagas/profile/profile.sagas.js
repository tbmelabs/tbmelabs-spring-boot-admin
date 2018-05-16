// @flow

import {takeEvery} from 'redux-saga';

import axios from 'axios';

import {REQUEST_PROFILE, setProfileAction} from '../../actions/profile';

function* requestProfile() {
  const response = yield axios.get('profile');
  yield put(setProfileAction(response.data));
}

export function* requestProfileSaga() {
  yield takeEvery(REQUEST_PROFILE, requestProfile);
}
