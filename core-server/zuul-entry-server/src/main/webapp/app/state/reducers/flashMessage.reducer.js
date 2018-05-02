// @flow

import {ADD_FLASH_MESSAGE, REMOVE_FLASH_MESSAGE} from '../actions/flashmessage';

import shortid from 'shortid';
import findIndex from 'lodash/findIndex';

export type FlashMessageType = {
  id: number;
  type: string;
  title: string;
  text: string;
};

export type FlashMessageState = FlashMessageType[];

const initialState: FlashMessageState = [];

export default (state: FlashMessageState = initialState, action: { type: string, payload: FlashMessageType | number }): FlashMessageState => {
  switch (action.type) {
    case ADD_FLASH_MESSAGE:
      const flashMessagePayload: FlashMessageType = ((action.payload: any): FlashMessageType);

      return [
        ...state,
        {
          id: shortid.generate(),
          type: flashMessagePayload.type,
          title: flashMessagePayload.title,
          text: flashMessagePayload.text
        }
      ];
    case REMOVE_FLASH_MESSAGE:
      const numberPayload: number = ((action.payload: any): number);

      const index = findIndex(state, {id: action.payload});

      if (index >= 0) {
        return [
          ...state.slice(0, index),
          ...state.slice(index + 1)
        ];
      }

      return [...state];
    default:
      return state;
  }
};