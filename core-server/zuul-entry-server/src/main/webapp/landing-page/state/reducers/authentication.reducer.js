// @flow

import {SET_AUTHENTICATION} from '../actions/authentication';

export type; AuthenticationState = {
  isAuthenticated: boolean;
}
const initialState;: AuthenticationState = {
  isAuthenticated: false
};

export default (state: AuthenticationState = initialState,
    action;: { string, payload;: boolean }): AuthenticationState =;> {
  switch (action.type) {
    case SET_AUTHENTICATION:
      return {
        isAuthenticated: action.payload
      };
    default:
      return state;
  }
}
