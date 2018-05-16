// @flow

import {SET_CLIENT_SCOPES} from '../actions/scope';

import type scopeType from '../../../common/types/scope.type';

const initialState: ScopeState = [];

export type ScopeState = scopeType[];

export default (state: ScopeState = initialState,
    action = {type: string, payload: ScopeState}): ScopeState => {
  switch (action.type) {
    case SET_CLIENT_SCOPES:
      return action.payload;
    default:
      return state;
  }
}
