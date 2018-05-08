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
import {
  addFlashMessageAction,
  removeFlashMessageAction
} from '../../actions/flashmessage';

import {getTexts} from '../../selectors/language';

import translateAuthenticationError from '../../../utils/translateAuthenticationError';
import {getFlashMessageByUid} from "../../selectors/flashmessage";

const SIGNIN_FAILED_ERROR_UID = shortid.generate();

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
  const existingFlashMessage = getFlashMessageByUid(getStore().getState(),
      SIGNIN_FAILED_ERROR_UID);

  if (existingFlashMessage !== null) {
    yield put(removeFlashMessageAction(existingFlashMessage.id));
  }

  yield put(addFlashMessageAction({
    uid: SIGNIN_FAILED_ERROR_UID,
    type: 'danger',
    title: getTexts(getStore().getState())['signin'].errors.title,
    text: translateAuthenticationError(action.payload)
  }));
}

export function* signinUserFailedSaga(): Generator<any, void, any> {
  yield takeEvery(SIGNIN_FAILED, signinUserFailed);
}
