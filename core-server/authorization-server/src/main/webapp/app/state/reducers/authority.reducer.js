// @flow

import {SET_CLIENT_AUTHORITIES} from '../actions/authority';

import {type authorityType} from '../../../common/types/authority.type';

const initialState: AuthorityState = {
  content: []
};

export type AuthorityState = {
  content: authorityType[];
  last?: boolean;
  totalPages?: number;
  totalElements?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  sort?: string;
  first?: boolean
};

export default (state: AuthorityState = initialState,
    action: { type: string, payload: AuthorityState }): AuthorityState => {
  switch (action.type) {
    case SET_CLIENT_AUTHORITIES:
      return action.payload;
    default:
      return state;
  }
}
