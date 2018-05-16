// @flow

import getStore from '../../../getStore';

import {requestClientsAction} from '../../actions/client';
import {type clientType} from '../../../../common/types/client.type';

export function requestClients() {
  getStore().dispatch(requestClientsAction());
}

export function saveClient(client: clientType) {
  throw 'This function is currently not implement!';
}
