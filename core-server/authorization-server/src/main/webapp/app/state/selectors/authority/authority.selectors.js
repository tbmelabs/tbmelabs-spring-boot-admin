// @flow

import {type ApplicationState} from '../../reducers';
import type {AuthorityState} from '../../reducers/authority.reducer';

export function getClientAuthorities(state: ApplicationState): AuthorityState {
  return state.authorities;
}
