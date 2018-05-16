// @flow

import {Action} from 'redux';

import {
  REQUEST_CLIENT_AUTHORITIES,
  REQUEST_CLIENT_AUTHORITIES_FAILED,
  SET_CLIENT_AUTHORITIES
} from './authority.types';

import {type authorityType} from '../../../../common/types/authority.type';

export function setClientAuthoritiesAction(authorities: authorityType[]): Action {
  return {
    type: SET_CLIENT_AUTHORITIES,
    payload: authorities
  };
}

export function requestClientAuthoritiesAction(): Action {
  return {
    type: REQUEST_CLIENT_AUTHORITIES
  };
}

export function requestClientAuthoritiesFailedAction(): Action {
  return {
    type: REQUEST_CLIENT_AUTHORITIES_FAILED
  };
}
