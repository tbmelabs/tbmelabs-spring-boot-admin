// @flow

import getStore from '../../../getStore';

import {type clientType} from '../../../../common/types/client.type';

import {getClients} from '../../selectors/client';

import {
  deleteClientAction,
  requestClientsAction,
  saveClientAction,
  updateClientAction
} from '../../actions/client';

import getOneClient from '../../../utils/client/getOneClient';

export function requestClient(id: number): Promise<clientType> {
  const fetchedClient = getClients(getStore().getState()).filter(
      (client: clientType) => client.id == id)[0];

  if (fetchedClient !== undefined) {
    return Promise.resolve(fetchedClient);
  }

  return getOneClient(id);
}

export function requestClients() {
  getStore().dispatch(requestClientsAction());
}

export function saveClient(client: clientType) {
  getStore().dispatch(saveClientAction(client));
}

export function updateClient(client: clientType) {
  getStore().dispatch(updateClientAction(client));
}

export function deleteClient(client: clientType) {
  getStore().dispatch(deleteClientAction(client));
}
