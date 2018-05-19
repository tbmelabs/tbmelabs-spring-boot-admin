// @flow

import {Action} from 'redux';

import {REQUEST_CLIENTS, SET_CLIENTS} from './client.types';

import {type ClientState} from '../../reducers/client.reducer';

export function setClientsAction(page: ClientState): Action {
  return {
    type: SET_CLIENTS,
    payload: page
  };
}

export function requestClientsAction(): Action {
  return {
    type: REQUEST_CLIENTS
  };
}
