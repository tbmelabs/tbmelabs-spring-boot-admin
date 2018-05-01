// @flow

import ObjectLiteralReduxAction from '../ObjectLiteralReduxAction';

import {SIGNOUT_USER} from './signout.types';

export class SignoutUserAction extends ObjectLiteralReduxAction {
  type = SIGNOUT_USER;
}
