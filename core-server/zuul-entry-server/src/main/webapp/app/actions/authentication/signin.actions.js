// @flow

import ObjectLiteralReduxAction from '../ObjectLiteralReduxAction';

import {SIGNIN_USER} from './signin.types';

export class SigninUserAction extends ObjectLiteralReduxAction {
  type = SIGNIN_USER;
}