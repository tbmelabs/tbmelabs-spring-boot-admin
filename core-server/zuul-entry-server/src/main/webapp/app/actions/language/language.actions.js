// @flow

import {SET_LANGUAGE} from './language.types';

export function setCurrentLanguage(language: string) {
  return {
    type: SET_LANGUAGE,
    language
  }
}