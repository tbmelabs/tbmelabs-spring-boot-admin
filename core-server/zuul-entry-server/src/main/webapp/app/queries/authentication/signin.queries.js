// @flow

import getStore from '../../getStore';

import {SigninUserAction} from '../../actions/authentication';

const store = getStore();

export function signinUser() {
  store.dispatch(new SigninUserAction());
}