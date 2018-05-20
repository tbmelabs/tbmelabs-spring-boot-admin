// @flow

import {Action} from 'redux';

import {
  REQUEST_CLIENT_AUTHORITIES,
  SET_CLIENT_AUTHORITIES
} from './authority.types';

import {type AuthorityState} from '../../reducers/authority.reducer';

export function setClientAuthoritiesAction(page: AuthorityState): Action {
  return {
    type: SET_CLIENT_AUTHORITIES,
    payload: page
  };
}

export function requestClientAuthoritiesAction(): Action {
  return {
    type: REQUEST_CLIENT_AUTHORITIES
  };
}
