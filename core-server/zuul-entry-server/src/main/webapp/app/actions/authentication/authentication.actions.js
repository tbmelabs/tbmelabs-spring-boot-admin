// @flow

import ObjectLiteralReduxAction from '../ObjectLiteralReduxAction';

import {REQUEST_AUTHENTICATION, SET_AUTHENTICATION} from './authentication.types';

export class SetAuthenticationAction extends ObjectLiteralReduxAction {
  type = SET_AUTHENTICATION;

  constructor(payload: boolean) {
    super(payload);
  }
}

export class RequestAuthenticationAction extends ObjectLiteralReduxAction {
  type = REQUEST_AUTHENTICATION;
}