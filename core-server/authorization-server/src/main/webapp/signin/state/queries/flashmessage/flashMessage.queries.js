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

const store = getStore();

export function getFlashMessages(): FlashMessageState {
  return store.getState().flashMessages;
}

export function addFlashMessage(flashMessage: FlashMessageType) {
  store.dispatch(addFlashMessageAction(flashMessage));
}

export function removeFlashMessage(id: number) {
  store.dispatch(removeFlashMessageAction(id));
}
