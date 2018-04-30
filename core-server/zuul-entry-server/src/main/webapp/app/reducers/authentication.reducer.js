// @flow

import type AuthenticationState from './types/authentication.state';
import {SET_AUTHENTICATION} from '../actions/authentication';

const initialState: AuthenticationState = {
  isAuthenticated: false
};

export default (state: AuthenticationState = initialState, action: { payload: boolean } = {}): AuthenticationState => {
  switch (action.type) {
    case SET_AUTHENTICATION:
      return {
        isAuthenticated: action.payload
      };
    default:
      return state;
  }
};