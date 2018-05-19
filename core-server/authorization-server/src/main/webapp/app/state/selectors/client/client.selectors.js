// @flow

import {type ApplicationState} from '../../reducers';

import {type clientType} from '../../../../common/types/client.type';

export function getClients(state: ApplicationState): clientType[] {
  return state.clients.content;
}
