// @flow

import {SET_PROFILE} from '../actions/profile';

import type profileType from '../../../common/types/profile.type';

const initialState: ProfileState = {};

export type ProfileState = profileType;

export default (state: ProfileState = initialState,
    action = {type: string, payload: profileType}): ProfileState => {
  switch (action.type) {
    case SET_PROFILE:
      return action.payload;
    default:
      return state;
  }
}
