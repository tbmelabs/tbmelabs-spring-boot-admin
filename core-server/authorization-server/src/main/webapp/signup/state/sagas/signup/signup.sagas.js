// @flow

import {Action} from 'redux';
import {put, takeEvery} from 'redux-saga/effects';

import axios from 'axios/index';

import shortid from 'shortid';

import type userType from '../../../../common/types/userType';

import getStore from '../../../../signin/getStore';

import {getTexts} from '../../../../signin/state/selectors/language';
import {
  SIGNUP_USER,
  SIGNUP_USER_FAILED,
  SIGNUP_USER_SUCCEED,
  signupUserFailedAction,
  signupUserSucceedAction
} from '../../actions/signup';
import {addFlashMessageAction} from '../../../../signin/state/actions/flashmessage';

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
  const texts = getTexts(getStore().getState())['signup'];

  yield put(addFlashMessageAction({
    uid: SIGNUP_FAILED_ERROR_UID,
    type: 'success',
    title: texts.signup_succeed_title,
    text: texts.signup_succeed_text
  }));
}

export function* signupUserSucceedSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNUP_USER_SUCCEED, signupUserSucceed);
}

function* signupUserFailed(action: Action) {
  const texts = getTexts(getStore().getState())['signup'];

  yield put(addFlashMessageAction({
    uid: SIGNUP_FAILED_ERROR_UID,
    type: 'danger',
    title: texts.errors.title,
    text: action.payload
  }));
}

export function* signupUserFailedSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNUP_USER_FAILED, signupUserFailed);
}
