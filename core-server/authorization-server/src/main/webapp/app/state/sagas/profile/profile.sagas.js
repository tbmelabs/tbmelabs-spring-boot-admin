// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import {REQUEST_PROFILE, setProfileAction} from '../../actions/profile';
import type {profileType} from '../../../../common/types/profile.type';

function* requestProfile() {
  const response: AxiosResponse = yield axios.get('profile');
  yield put(setProfileAction((response.data: profileType)));
}

export function* requestProfileSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_PROFILE, requestProfile);
}
