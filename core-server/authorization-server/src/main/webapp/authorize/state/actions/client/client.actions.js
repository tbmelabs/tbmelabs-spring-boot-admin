// @flow

import {Action} from 'redux';

import {
  REQUEST_CLIENT_SCOPES,
  REQUEST_CLIENT_SCOPES_FAILED,
  SET_CLIENT_SCOPES
} from './client.types';

export function setClientScopesAction(scopes: string[]): Action {
  return {
    type: SET_CLIENT_SCOPES,
    payload: scopes
  };
}

export function requestClientScopesAction(clientId: string): Action {
  return {
    type: REQUEST_CLIENT_SCOPES,
    payload: clientId
  };
}

export function requestClientScopesFailedAction(): Action {
  return {
    type: REQUEST_CLIENT_SCOPES_FAILED
  };
}
