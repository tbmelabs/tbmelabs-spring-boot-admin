// @flow

import getStore from '../../../getStore';

import {requestClientsAction, saveClientAction} from '../../actions/client';
import {type clientType} from '../../../../common/types/client.type';

export function requestClients() {
  getStore().dispatch(requestClientsAction());
}

export function saveClient(client: clientType) {
  getStore().dispatch(saveClientAction(client));
}
