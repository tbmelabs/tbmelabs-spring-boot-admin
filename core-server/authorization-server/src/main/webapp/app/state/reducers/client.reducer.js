// @flow

import {SET_CLIENTS} from '../actions/client';

import {type clientType} from '../../../common/types/client.type';

const initialState: ClientState = [];

export type ClientState = { ...clientType }[];

export default (state: ClientState = initialState,
    action: { type: string, payload: ClientState }): ClientState => {
  switch (action.type) {
    case SET_CLIENTS:
      return action.payload;
    default:
      return state;
  }
}
