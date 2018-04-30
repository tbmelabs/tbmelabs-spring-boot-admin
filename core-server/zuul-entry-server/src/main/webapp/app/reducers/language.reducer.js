// @flow

import type LanguageState from './types/language.state';
import {SET_LANGUAGE} from '../actions/language';

const en = require('../config/languages/en.json');

const initialState: LanguageState = {
  name: en.language,
  texts: en,
  id: 'en'
}

export default (state: LanguageState = initialState, action: { payload: string } = {}): LanguageState => {
  switch (action.type) {
    case SET_LANGUAGE:
      try {
        const language = require(`../config/languages/${action.payload}.json`);

        return {
          name: language.name,
          texts: language,
          id: action.payload
        }
      } catch (error) {
        return state;
      }
    default:
      return state;
  }
}