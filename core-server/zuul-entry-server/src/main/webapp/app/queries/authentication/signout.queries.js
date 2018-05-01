// @flow

import getStore from '../../getStore';

import {signoutUserAction} from '../../actions/authentication';

const store = getStore();

export function signoutUser() {
  store.dispatch(signoutUserAction());
}