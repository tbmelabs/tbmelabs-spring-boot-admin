// @flow

import getStore from '../../../getStore';

import {signupUserAction} from '../../actions/signup';

import type userType from '../../../../common/types/userType';

export function signupUser(user: userType) {
  getStore().dispatch(signupUserAction(user));
}
