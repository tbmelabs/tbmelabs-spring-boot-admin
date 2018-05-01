// @flow

import {Action} from 'redux';

import {SIGNOUT_USER} from './signout.types';

export function signoutUserAction(): Action {
  return {
    type: SIGNOUT_USER
  };
}
