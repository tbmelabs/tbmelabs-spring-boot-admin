// @flow

import {SET_CLIENTS} from '../actions/client';

import {type clientType} from '../../../common/types/client.type';

const initialState: ClientState = {
  content: []
};

export type ClientState = {
  content: clientType[];
  last?: boolean;
  totalPages?: number;
  totalElements?: number;
  size?: number;
  number?: number;
  numberOfElements?: number;
  sort?: string;
  first?: boolean
};

export default (state: ClientState = initialState,
    action: { type: string, payload: ClientState }): ClientState => {
  switch (action.type) {
    case SET_CLIENTS:
      return action.payload;
    default:
      return state;
  }
}
