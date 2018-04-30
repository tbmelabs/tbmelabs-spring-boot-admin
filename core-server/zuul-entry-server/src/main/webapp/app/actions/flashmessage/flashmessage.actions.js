// @flow

import {Action} from 'redux';

import type FlashMessage from '../../reducers/types/flashMessage.type';
import {ADD_FLASH_MESSAGE, REMOVE_FLASH_MESSAGE} from './flashmessage.types';

export class AddFlashMessageAction implements Action {
  +type = ADD_FLASH_MESSAGE;

  constructor(payload: FlashMessage) {
  }
}

export class RemoveFlashMessageAction implements Action {
  +type = REMOVE_FLASH_MESSAGE;

  constructor(payload: number) {
  }
}
