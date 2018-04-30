// @flow

import {Action} from 'redux';

import {SET_LANGUAGE} from './language.types';

export class SetLanguageAction implements Action {
  constructor(payload: string) {
    this.type = SET_LANGUAGE;

    return {...this};
  }
}
