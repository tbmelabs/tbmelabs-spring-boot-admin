// @flow

import {Action} from 'redux';

import {type FlashMessageType} from '../../reducers/flashMessage.reducer';
import {ADD_FLASH_MESSAGE, REMOVE_FLASH_MESSAGE} from './flashmessage.types';

export class AddFlashMessageAction implements Action {
  constructor(payload: FlashMessageType) {
    this.type = ADD_FLASH_MESSAGE;

    return {...this};
  }
}

export class RemoveFlashMessageAction implements Action {
  constructor(payload: number) {
    this.type = REMOVE_FLASH_MESSAGE;

    return {...this};
  }
}
