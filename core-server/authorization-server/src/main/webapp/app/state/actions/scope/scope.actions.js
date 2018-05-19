// @flow

import {Action} from 'redux';

import {
  REQUEST_CLIENT_SCOPES,
  REQUEST_CLIENT_SCOPES_FAILED,
  SET_CLIENT_SCOPES
} from './scope.types';

import type {ScopeState} from '../../reducers/scope.reducer';

export function setClientScopesAction(page: ScopeState): Action {
  return {
    type: SET_CLIENT_SCOPES,
    payload: page
  };
}

export function requestClientScopesAction(): Action {
  return {
    type: REQUEST_CLIENT_SCOPES
  };
}

export function requestClientScopesFailedAction(): Action {
  return {
    type: REQUEST_CLIENT_SCOPES_FAILED
  };
}
