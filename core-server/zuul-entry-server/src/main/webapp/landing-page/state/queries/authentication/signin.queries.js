// @flow

import getStore from '../../../getStore';

import {signinUserAction} from '../../actions/authentication';

export function signinUser() {
  getStore().dispatch(signinUserAction());
}
