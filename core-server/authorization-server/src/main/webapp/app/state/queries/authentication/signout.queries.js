// @flow

import getStore from '../../../getStore';

import {signoutUserAction} from '../../actions/authentication';

export function signoutUser() {
  getStore().dispatch(signoutUserAction());
}
