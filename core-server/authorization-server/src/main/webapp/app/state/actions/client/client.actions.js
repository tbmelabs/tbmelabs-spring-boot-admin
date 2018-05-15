// @flow

import {Action} from 'redux';

import {REQUEST_CLIENTS, SET_CLIENTS} from './client.types';

import type clientType from '../../../../common/types/client.type';

export function setClientsAction(clients: clientType[]): Action {
  return {
    type: SET_CLIENTS
  }
}

export function requestClientsAction(): Action {
  return {
    type: REQUEST_CLIENTS
  }
}
