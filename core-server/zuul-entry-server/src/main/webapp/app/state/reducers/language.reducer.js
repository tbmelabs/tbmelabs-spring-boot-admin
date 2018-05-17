// @flow

import {SET_LANGUAGE} from '../actions/language';

const en = require('../../config/i18n/en.json');

export type LanguageState = {
  name: string;
  texts: any;
  id: string;
};

const initialState: LanguageState = {
  name: en.language,
  texts: en,
  id: 'en'
};

export default (state: LanguageState = initialState,
    action: { type: string, payload: string }): LanguageState => {
  switch (action.type) {
    case SET_LANGUAGE:
      try {
        // $FlowFixMe
        const language = require(
            `./app/config/i18n`);

        return ({
          name: language.name,
          texts: language,
          id: action.payload
        }: LanguageState);
      } catch (error) {
        return state;
      }
    default:
      return state;
  }
};
