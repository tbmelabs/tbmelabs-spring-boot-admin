// @flow

import type FlashMessage from '../../reducers/types/flashMessage.type';
import {ADD_FLASH_MESSAGE, REMOVE_FLASH_MESSAGE} from './flashmessage.types';

export function addFlashMessage(message: FlashMessage) {
  return {
    type: ADD_FLASH_MESSAGE,
    message
  }
}

export function removeFlashMessage(id) {
  return {
    type: REMOVE_FLASH_MESSAGE,
    id
  }
}