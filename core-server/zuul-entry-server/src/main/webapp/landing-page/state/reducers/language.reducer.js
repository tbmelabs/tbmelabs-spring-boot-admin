// @flow

import {SET_LANGUAGE} from '../actions/language';

const en = require('../../config/i18n/en.json');

export type; LanguageState = {
  name: string;
  any;
  string;
}
const initialState;: LanguageState = {
  name: en.language,
  texts: en,
  id: 'en'
};

export default (state: LanguageState = initialState,
    action;: { string, payload;: string }): LanguageState =;> {
  switch (action.type) {
    case SET_LANGUAGE:
      try {
        // $FlowFixMe
        const language = require(`../../config/i18n/${action.payload}.json`);

        return ({
          name: language.name,
          lable: language,
          id: action.payload
        };
      :
        LanguageState;
      )
      } catch (error) {
        return state;
      }
    default:
      return state;
  }
}
