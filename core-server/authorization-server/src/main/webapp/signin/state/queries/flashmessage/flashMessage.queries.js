// @flow

import getStore from '../../../getStore';

import {
  addFlashMessageAction,
  removeFlashMessageAction,
} from '../../actions/flashmessage';

import {type FlashMessageType} from '../../reducers/flashMessage.reducer';

export function addFlashMessage(flashMessage: FlashMessageType) {
  getStore().dispatch(addFlashMessageAction(flashMessage));
}

export function removeFlashMessage(id: number) {
  getStore().dispatch(removeFlashMessageAction(id));
}
