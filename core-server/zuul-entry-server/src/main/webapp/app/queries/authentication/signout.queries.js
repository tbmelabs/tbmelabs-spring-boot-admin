// @flow

import getStore from '../../getStore';

import {SignoutUserAction} from '../../actions/authentication';

const store = getStore();

export function signoutUser() {
  store.dispatch(new SignoutUserAction());
}