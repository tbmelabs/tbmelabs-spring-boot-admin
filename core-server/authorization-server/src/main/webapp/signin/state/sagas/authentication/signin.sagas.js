// @flow

import {Action} from 'redux';
import {put, takeEvery, takeLatest} from 'redux-saga/effects';

import axios from 'axios';

import shortid from 'shortid';

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

const SIGNIN_FAILED_ERROR_UID = shortid.generate();

function* signinUser(action: { type: string, payload: { username: string, password: string } }) {
  var formData = new FormData();
  formData.append('username', action.payload.username);
  formData.append('password', action.payload.password);

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

function* signinUserSucceed(action: { type: string, payload: string }) {
  window.location.replace(action.payload);
}

export function* signinUserSucceedSaga(): Generator<any, void, any> {
  yield takeLatest(SIGNIN_SUCCEED, signinUserSucceed);
}

function* signinUserFailed(action: Action) {
  const texts = getTexts(getStore().getState())['signin'];

  yield put(addFlashMessageAction({
    uid: SIGNIN_FAILED_ERROR_UID,
    type: 'danger',
    title: texts.errors.title,
    text: translateAuthenticationError(action.payload)
  }));
}

export function* signinUserFailedSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNIN_FAILED, signinUserFailed);
}
