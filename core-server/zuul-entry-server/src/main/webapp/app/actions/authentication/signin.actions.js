// @flow

import {Action} from 'redux';

import {SIGNIN_USER} from './signin.types';

export class SigninUserAction implements Action {
  constructor() {
    this.type = SIGNIN_USER;

    return {...this};
  }
}