// @flow

import {type ApplicationState} from '../../reducers';
import {type ClientState} from '../../reducers/client.reducer';

export function getClient(state: ApplicationState): ClientState {
  return state.client;
}
