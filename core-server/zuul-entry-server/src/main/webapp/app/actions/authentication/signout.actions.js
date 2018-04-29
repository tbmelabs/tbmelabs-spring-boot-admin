// @flow

import {SIGNOUT_USER} from './signout.types';

export function signoutUser() {
  return {
    type: SIGNOUT_USER
  };
}