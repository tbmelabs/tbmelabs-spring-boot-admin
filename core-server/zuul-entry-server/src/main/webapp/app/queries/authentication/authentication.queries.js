// @flow

import getStore from '../../getStore';

import {RequestAuthenticationAction} from '../../actions/authentication';

const store = getStore();

export function getIsAuthenticated(): boolean {
  return store.getState().authentication.isAuthenticated;
}

export function requestAuthentication() {
  store.dispatch(new RequestAuthenticationAction());
}