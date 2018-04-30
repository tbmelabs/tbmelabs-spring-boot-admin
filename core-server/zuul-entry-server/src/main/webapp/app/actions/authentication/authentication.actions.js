// @flow

import {Action} from 'redux';

import {REQUEST_AUTHENTICATION, SET_AUTHENTICATION} from './authentication.types';

export class SetAuthenticationAction implements Action {
  constructor(payload: boolean) {
    this.type = SET_AUTHENTICATION;
  }
}

export class RequestAuthenticationAction implements Action  {
  constructor() {
    this.type = REQUEST_AUTHENTICATION;
  }
}