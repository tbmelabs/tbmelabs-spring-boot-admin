// @flow

import getStore from '../../../getStore';

import {
  addFlashMessageAction,
  removeFlashMessageAction,
} from '../../actions/flashmessage';

import {
  type FlashMessageState,
  type FlashMessageType
} from '../../reducers/flashMessage.reducer';

export function getFlashMessages(): FlashMessageState {
  return getStore().getState().flashMessages;
}

export function addFlashMessage(flashMessage: FlashMessageType) {
  getStore().dispatch(addFlashMessageAction(flashMessage));
}

export function removeFlashMessage(id: number) {
  getStore().dispatch(removeFlashMessageAction(id));
}
