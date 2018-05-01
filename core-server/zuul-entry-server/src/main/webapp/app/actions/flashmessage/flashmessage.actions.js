// @flow

import ObjectLiteralReduxAction from '../ObjectLiteralReduxAction';

import {type FlashMessageType} from '../../reducers/flashMessage.reducer';
import {ADD_FLASH_MESSAGE, REMOVE_FLASH_MESSAGE} from './flashmessage.types';

export class AddFlashMessageAction extends ObjectLiteralReduxAction {
  type = ADD_FLASH_MESSAGE;

  constructor(payload: FlashMessageType) {
    super(payload);
  }
}

export class RemoveFlashMessageAction extends ObjectLiteralReduxAction {
  type = REMOVE_FLASH_MESSAGE;

  constructor(payload: number) {
    super(payload);
  }
}
