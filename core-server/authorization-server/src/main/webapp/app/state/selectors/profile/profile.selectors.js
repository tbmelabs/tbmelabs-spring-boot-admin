// @flow

import type {ApplicationState} from '../../reducers';

import type {ProfileState} from '../../reducers/profile.reducer';

export function getProfile(state: ApplicationState): ProfileState {
  return state.profile;
}
