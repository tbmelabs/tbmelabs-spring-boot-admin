// @flow

import type Authentication from './types/authentication.type';
import {SET_AUTHENTICATION} from '../actions/authentication';

const initialState: Authentication = {
  isAuthenticated: false
};

export default (state: Authentication = initialState, action: { payload: boolean } = {}): Authentication => {
  switch (action.type) {
    case SET_AUTHENTICATION:
      return {
        isAuthenticated: action.payload
      };
    default:
      return state;
  }
};