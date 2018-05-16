// @flow

import {SET_CLIENT_GRANT_TYPES} from '../actions/grantType';

import type grantTypeType from '../../../common/types/grantType.type';

const initialState: GrantTypeState = [];

export type GrantTypeState = grantTypeType[];

export default (state: GrantTypeState = initialState,
    action = {type: string, payload: GrantTypeState}): GrantTypeState => {
  switch (action.type) {
    case SET_CLIENT_GRANT_TYPES:
      return action.payload;
    default:
      return state;
  }
}
