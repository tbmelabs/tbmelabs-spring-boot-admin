// @flow

import {Action} from 'redux';

import {REQUEST_PROFILE, SET_PROFILE} from './profile.types';

import type profileType from '../../../../common/types/profile.type';

export function setProfileAction(profile: profileType): Action {
  return {
    type: SET_PROFILE,
    payload: profile
  }
}

export function requestProfileAction(): Action {
  return {
    type: REQUEST_PROFILE
  }
}
