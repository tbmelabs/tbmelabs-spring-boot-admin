// @flow

import {REQUEST_AUTHENTICATION, SET_AUTHENTICATION} from './authentication.types';

export function setAuthentication(state: boolean) {
  return {
    type: SET_AUTHENTICATION,
    state
  };
}

export function requestAuthentication() {
  return {
    type: REQUEST_AUTHENTICATION
  };
}