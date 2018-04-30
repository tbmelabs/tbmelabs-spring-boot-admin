// @flow

import {Action} from 'redux';

import {REQUEST_AUTHENTICATION, SET_AUTHENTICATION} from './authentication.types';

export class SetAuthenticationAction implements Action {
  +type = SET_AUTHENTICATION;

  constructor(payload: boolean) {
  }
}

export class RequestAuthenticationAction implements Action  {
  +type = REQUEST_AUTHENTICATION;

  constructor() {
  }
}