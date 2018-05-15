// @flow

import {Action} from 'redux';

import {
  REQUEST_CLIENT_GRANT_TYPES,
  REQUEST_CLIENT_GRANT_TYPES_FAILED,
  SET_CLIENT_GRANT_TYPES
} from './grantType.types';

import type grantTypeType from '../../../../common/types/grantType.type';

export function setClientGrantTypesAction(grantTypes: grantTypeType[]): Action {
  return {
    type: SET_CLIENT_GRANT_TYPES,
    payload: grantTypes
  }
}

export function requestClientGrantTypesAction(): Action {
  return {
    type: REQUEST_CLIENT_GRANT_TYPES
  }
}

export function requestClientGrantTypesFailedAction(): Action {
  return {
    type: REQUEST_CLIENT_GRANT_TYPES_FAILED
  }
}
