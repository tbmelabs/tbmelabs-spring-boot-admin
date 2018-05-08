// @flow

import type {ApplicationState} from '../../reducers';

export function getIsAuthenticated(state: ApplicationState): boolean {
  return state.authentication.isAuthenticated;
}
