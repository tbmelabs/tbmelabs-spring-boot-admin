'use strict';

import {SET_ACCESS_TOKEN} from '../actions/types';

import isEmpty from 'validator/lib/isEmpty';

const initialState = {
  isAuthenticated: false,
  accessToken: ''
};

export default (state = initialState, action = {}) => {
  switch (action.type) {
    case SET_ACCESS_TOKEN:
      return {
        isAuthenticated: !isEmpty(action.token),
        accessToken: action.token
      };
    default:
      return state;
  }
}