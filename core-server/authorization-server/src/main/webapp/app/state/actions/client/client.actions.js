// @flow

import {Action} from 'redux';

import {
  REQUEST_CLIENTS,
  SAVE_CLIENT,
  SAVE_CLIENT_SUCCEED,
  SET_CLIENTS
} from './client.types';

import {type ClientState} from '../../reducers/client.reducer';

import {type clientType} from '../../../../common/types/client.type';

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

export function saveClientAction(client: clientType): Action {
  return {
    type: SAVE_CLIENT,
    payload: client
  };
}

export function saveClientSucceedAction(): Action {
  return {
    type: SAVE_CLIENT_SUCCEED
  };
}
