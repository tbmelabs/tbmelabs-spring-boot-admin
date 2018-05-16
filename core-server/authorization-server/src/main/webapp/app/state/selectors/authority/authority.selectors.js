// @flow

import {type ApplicationState} from '../../reducers';
import type {GrantTypeState} from '../../reducers/grantType.reducer';

export function getClientGrantTypes(state: ApplicationState): GrantTypeState {
  return state.grantTypes;
}
