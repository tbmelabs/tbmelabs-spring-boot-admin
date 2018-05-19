// @flow

import {type ApplicationState} from '../../reducers';

import {type authorityType} from '../../../../common/types/authority.type';

export function getClientAuthorities(state: ApplicationState): authorityType[] {
  return state.authorities.content;
}
