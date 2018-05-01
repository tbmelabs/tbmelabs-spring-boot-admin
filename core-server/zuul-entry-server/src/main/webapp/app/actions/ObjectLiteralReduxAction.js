// @flow

import {Action} from 'redux';

export default class ObjectLiteralReduxAction implements Action {
  constructor(payload: any) {
    return {...this};
  }
}