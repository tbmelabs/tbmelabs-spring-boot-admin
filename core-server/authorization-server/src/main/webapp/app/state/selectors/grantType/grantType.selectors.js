// @flow

import {type ApplicationState} from '../../reducers';

import {type grantTypeType} from '../../../../common/types/grantType.type';

export function getClientGrantTypes(state: ApplicationState): grantTypeType[] {
  return state.grantTypes.content;
}
