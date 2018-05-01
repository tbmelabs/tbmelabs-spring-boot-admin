// @flow

import getStore from '../../getStore';

import {AddFlashMessageAction, RemoveFlashMessageAction} from '../../actions/flashmessage';
import {type FlashMessageType, type FlashMessageState} from '../../reducers/flashMessage.reducer';

const store = getStore();

export function getFlashMessages(): FlashMessageState {
  return store.getState().flashMessages
}

export function addFlashMessage(flashMessage: FlashMessageType) {
  store.dispatch(new AddFlashMessageAction(flashMessage));
}

export function removeFlashMessage(id: number) {
  store.dispatch(new RemoveFlashMessageAction(id));
}
