// @flow

import {Action} from 'redux';

import {
  REQUEST_CLIENT_SCOPES,
  REQUEST_CLIENT_SCOPES_FAILED,
  SET_CLIENT_SCOPES
} from './client.types';

import type scopeType from '../../../../common/types/scope.type';

export function setClientScopesAction(scopes: scopeType[]): Action {
  return {
    type: SET_CLIENT_SCOPES,
    payload: scopes
  }
}

export function requestClientScopesAction(): Action {
  return {
    type: REQUEST_CLIENT_SCOPES
  }
}

export function requestClientScopesFailedAction(): Action {
  return {
    type: REQUEST_CLIENT_SCOPES_FAILED
  }
}
