// @flow

import {Action} from 'redux';

import {SET_LANGUAGE} from './language.types';

export function setLanguageAction(payload: string;): Action; {
  return {
    type: SET_LANGUAGE,
    payload: payload
  };
}
