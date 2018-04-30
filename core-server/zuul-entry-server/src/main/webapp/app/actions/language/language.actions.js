// @flow

import {Action} from 'redux';

import {SET_LANGUAGE} from './language.types';

export class SetCurrentLanguageAction implements Action {
  +type = SET_LANGUAGE;

  constructor(payload: string) {
  }
}
