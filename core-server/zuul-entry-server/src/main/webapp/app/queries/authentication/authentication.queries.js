// @flow

import getStore from '../../getStore';

import {requestAuthenticationAction} from '../../actions/authentication';

const store = getStore();

export function getIsAuthenticated(): boolean {
  return store.getState().authentication.isAuthenticated;
}

export function requestAuthentication() {
  store.dispatch(requestAuthenticationAction());
}