// @flow

import {SIGNIN_USER} from './signin.types';

export function signinUser() {
  return {
    type: SIGNIN_USER
  };
}