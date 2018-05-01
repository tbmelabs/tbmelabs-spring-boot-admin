// @flow

import {Action} from 'redux';

import {REQUEST_AUTHENTICATION, SET_AUTHENTICATION} from './authentication.types';

export function setAuthenticationAction(payload: boolean): Action {
  return {
    type: SET_AUTHENTICATION,
    payload: payload
  };
}

export function requestAuthenticationAction(): Action {
  return {
    type: REQUEST_AUTHENTICATION
  };
}
