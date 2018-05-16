// @flow

import {Action} from 'redux';

import {
  SIGNUP_USER,
  SIGNUP_USER_FAILED,
  SIGNUP_USER_SUCCEED
} from './signup.types';

import {type userType} from '../../../../common/types/user.type';

export function signupUserAction(payload: userType): Action {
  return {
    type: SIGNUP_USER,
    payload: payload
  };
}

export function signupUserSucceedAction(): Action {
  return {
    type: SIGNUP_USER_SUCCEED
  };
}

export function signupUserFailedAction(): Action {
  return {
    type: SIGNUP_USER_FAILED
  };
}
