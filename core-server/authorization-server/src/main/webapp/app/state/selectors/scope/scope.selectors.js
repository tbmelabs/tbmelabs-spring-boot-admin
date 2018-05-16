// @flow

import {type ApplicationState} from '../../reducers';
import {type ScopeState} from '../../reducers/scope.reducer';

export function getClientScopes(state: ApplicationState): ScopeState {
  return state.scopes;
}
