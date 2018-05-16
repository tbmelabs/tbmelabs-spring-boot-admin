// @flow

import {SET_CLIENT_SCOPES} from '../actions/client/client.types';

export type ClientState = {
  clientId: string;
  scopes: string[];
};

const initialState: ClientState = {
  clientId: '',
  scopes: []
};

export default (state: ClientState = initialState,
    action: { type: string, payload: string[] }): ClientState => {
  switch (action.type) {
    case SET_CLIENT_SCOPES:
      return {
        ...state,
        scopes: [...action.payload]
      };
    default:
      return state;
  }
};
