// @flow

import {Action} from 'redux';
import {put, takeEvery} from 'redux-saga/effects';

import axios from 'axios/index';

import shortid from 'shortid';

import type userType from '../../../../common/types/userType';

import getStore from '../../../getStore';

import {getTexts} from '../../selectors/language';
import {
  SIGNUP_USER,
  SIGNUP_USER_FAILED,
  SIGNUP_USER_SUCCEED,
  signupUserFailedAction,
  signupUserSucceedAction
} from '../../actions/signup';
import {addFlashMessageAction} from '../../actions/flashmessage';

const SIGNUP_FAILED_ERROR_UID = shortid.generate();

function* signupUser(action: { type: string, payload: userType }) {
  try {
    const response = yield axios.post('signup/do-signup', {
      username: action.payload.username,
      email: action.payload.email,
      password: action.payload.password,
      confirmation: action.payload.confirmation
    });

    yield put(signupUserSucceedAction());
  } catch (error) {
    yield put(signupUserFailedAction());
  }
}

export function* signupUserSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNUP_USER, signupUser);
}

function* signupUserSucceed() {
  window.location.replace('signin?signup_succeed')
}

export function* signupUserSucceedSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNUP_USER_SUCCEED, signupUserSucceed);
}

function* signupUserFailed() {
  const texts = getTexts(getStore().getState()).signup;

  yield put(addFlashMessageAction({
    uid: SIGNUP_FAILED_ERROR_UID,
    type: 'danger',
    title: texts.errors.title,
    text: texts.errors.signup_failed_message
  }));
}

export function* signupUserFailedSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNUP_USER_FAILED, signupUserFailed);
}
