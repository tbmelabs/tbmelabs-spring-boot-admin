'use strict';

import {SET_PROFILE} from './types';

export function setProfile(profile) {
  return {
    type: SET_PROFILE,
    profile
  }
}