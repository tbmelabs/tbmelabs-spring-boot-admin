// @flow

import {Action} from 'redux';

import {SIGNIN_USER} from './signin.types';

export function signinUserAction(): Action {
  return {
    type: SIGNIN_USER
  };
}
