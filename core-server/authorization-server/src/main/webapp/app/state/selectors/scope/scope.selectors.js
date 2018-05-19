// @flow

import {type ApplicationState} from '../../reducers';

import {type scopeType} from '../../../../common/types/scope.type';

export function getClientScopes(state: ApplicationState): scopeType[] {
  return state.scopes.content;
}
