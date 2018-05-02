// @flow

import {Action} from 'redux';

import {type FlashMessageType} from '../../reducers/flashMessage.reducer';
import {ADD_FLASH_MESSAGE, REMOVE_FLASH_MESSAGE} from './flashmessage.types';

export function addFlashMessageAction(payload: FlashMessageType): Action {
  return {
    type: ADD_FLASH_MESSAGE,
    payload: payload
  };
}
export function removeFlashMessageAction(payload: number): Action {
  return {
    type: REMOVE_FLASH_MESSAGE,
    payload: payload
  };
}
