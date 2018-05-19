// @flow

import {type ApplicationState} from '../../reducers';
import {type ClientState} from '../../reducers/client.reducer';

export function getClients(state: ApplicationState): ClientState {
  return state.clients.content;
}
