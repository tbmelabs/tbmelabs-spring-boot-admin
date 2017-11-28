'use strict';

import {SET_CURRENT_LANGUAGE} from '../actions/types';

const en = require('../languages/en.json');

const initialState = {
  name: en.language,
  texts: en,
  id: 'en'
}

export default (state = initialState, action = {}) => {
  switch (action.type) {
    case SET_CURRENT_LANGUAGE:
      const language = require('../languages/' + action.language + '.json');

      return {
        name: language.name,
        texts: language,
        id: action.language
      }
    default:
      return state;
  }
}