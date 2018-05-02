// @flow

import getStore from '../../../getStore';

import {signinUserAction} from '../../actions/authentication';

const store = getStore();

export function signinUser() {
  store.dispatch(signinUserAction());
}
