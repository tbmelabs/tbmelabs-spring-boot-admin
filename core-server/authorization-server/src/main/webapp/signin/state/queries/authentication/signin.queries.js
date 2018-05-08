// @flow

import getStore from '../../../getStore';

import {signinUserAction} from '../../actions/authentication';

export function signinUser(credentials: { username: string, password: string }) {
  getStore().dispatch(signinUserAction(credentials));
}
