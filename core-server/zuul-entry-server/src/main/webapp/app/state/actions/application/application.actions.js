// @flow

import {Action} from 'redux';

import {LAUNCH_APPLICATION} from './application.types';

export function launchApplicationAction(): Action {
  return {
    type: LAUNCH_APPLICATION
  }
}
