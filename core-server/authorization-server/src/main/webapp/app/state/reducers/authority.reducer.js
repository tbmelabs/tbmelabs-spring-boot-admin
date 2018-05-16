// @flow

import {SET_CLIENT_AUTHORITIES} from '../actions/authority';

import type authorityType from '../../../common/types/authority.type';

const initialState: AuthorityState = [];

export type AuthorityState = authorityType[];

export default (state: AuthorityState = initialState,
    action = {type: string, payload: AuthorityState}): AuthorityState => {
  switch (action.type) {
    case SET_CLIENT_AUTHORITIES:
      return action.payload;
    default:
      return state;
  }
}
