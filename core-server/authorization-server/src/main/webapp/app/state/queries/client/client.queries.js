// @flow

import getStore from '../../../getStore';

import {requestClientsAction} from '../../actions/client';

export function requestClients() {
  getStore().dispatch(requestClientsAction());
}
