'use strict';

import {SET_ACCESS_TOKEN} from '../actions/types';

import isEmpty from 'lodash/isEmpty';

const initialState = {
  isAuthenticated: false,
  user: {}
};

export default (state = initialState, action = {}) => {
  switch (action.type) {
    case SET_ACCESS_TOKEN:
      return {
        isAuthenticated: !isEmpty(action.data),
        user: action.data
      };
    default:
      return state;
  }
}