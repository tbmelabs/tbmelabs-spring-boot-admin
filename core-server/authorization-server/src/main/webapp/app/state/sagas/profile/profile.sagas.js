// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {REQUEST_PROFILE, setProfileAction} from '../../actions/profile';

function* requestProfile() {
  const response: AxiosResponse = yield axios.get('profile');
  yield put(setProfileAction((response.data)));
}

export function* requestProfileSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_PROFILE, requestProfile);
}
