// @flow

import {Action} from 'redux';
import {put, takeEvery, takeLatest} from 'redux-saga/effects';

import axios from 'axios';

import getStore from '../../../getStore';

import {
  SIGNIN_FAILED,
  SIGNIN_SUCCEED,
  SIGNIN_USER,
  signinUserFailedAction,
  signinUserSucceedAction
} from '../../actions/authentication';
import {addFlashMessageAction} from '../../actions/flashmessage';

import {getTexts} from '../../selectors/language';

import translateAuthenticationError from '../../../utils/translateAuthenticationError';

function* signinUser(credentials: { username: string, password: string }) {
  var formData = new FormData();
  formData.append('username', credentials.username);
  formData.append('password', credentials.password);

  try {
    const response = yield axios.post('signin', formData, {
      headers: {
        'no-redirect': ''
      }
    });

    yield put(signinUserSucceedAction(response.headers['no-redirect']));
  } catch (error) {
    yield put(signinUserFailedAction(error.response.data.message));
  }
}

export function* signinUserSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNIN_USER, signinUser);
}

export function* signinUserSucceedSaga(): Generator<any, void, any> {
  yield takeLatest(SIGNIN_SUCCEED,
      (action: Action) => window.location.replace(action.payload));
}

function* signinUserFailed(action: Action) {
  yield put(addFlashMessageAction({
    type: 'danger',
    title: getTexts(getStore().getState())['signin'].errors.title,
    text: translateAuthenticationError(action.payload)
  }));
}

export function* signinUserFailedSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNIN_FAILED, signinUserFailed);
}
