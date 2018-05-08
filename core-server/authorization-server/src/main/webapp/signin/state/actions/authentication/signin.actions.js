// @flow

import {Action} from 'redux';

import {SIGNIN_FAILED, SIGNIN_SUCCEED, SIGNIN_USER} from './signin.types';

export function signinUserAction(payload: { username: string, password: string }): Action {
  return {
    type: SIGNIN_USER,
    payload: payload
  };
}

export function signinUserSucceedAction(payload: string): Action {
  return {
    type: SIGNIN_SUCCEED,
    payload: payload
  }
}

export function signinUserFailedAction(payload: string): Action {
  return {
    type: SIGNIN_FAILED,
    payload: payload
  }
}
