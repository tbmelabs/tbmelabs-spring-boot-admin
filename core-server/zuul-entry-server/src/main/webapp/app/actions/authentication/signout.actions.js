// @flow

import {Action} from 'redux';

import {SIGNOUT_USER} from './signout.types';

export class SignoutUserAction implements Action {
  +type = SIGNOUT_USER;

  constructor() {
  }
}
