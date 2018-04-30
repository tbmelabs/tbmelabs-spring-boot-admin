// @flow

import {Action} from 'redux';

import {SIGNIN_USER} from './signin.types';

export class SigninUserAction implements Action {
  +type = SIGNIN_USER;

  constructor() {
  }
}
