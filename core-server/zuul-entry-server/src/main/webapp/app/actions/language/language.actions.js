// @flow

import ObjectLiteralReduxAction from '../ObjectLiteralReduxAction';

import {SET_LANGUAGE} from './language.types';

export class SetLanguageAction extends ObjectLiteralReduxAction {
  type: string = SET_LANGUAGE;

  constructor(payload: string) {
    super(payload);
  }
}
