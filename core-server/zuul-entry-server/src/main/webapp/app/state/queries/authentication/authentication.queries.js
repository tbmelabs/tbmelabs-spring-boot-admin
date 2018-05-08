// @flow

import getStore from '../../../getStore';

import {requestAuthenticationAction} from '../../actions/authentication';

export function getIsAuthenticated(): boolean {
  return getStore().getState().authentication.isAuthenticated;
}

export function requestAuthentication() {
  getStore().dispatch(requestAuthenticationAction());
}
