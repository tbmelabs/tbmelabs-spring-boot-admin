// @flow

import getStore from '../../getStore';

import {AddFlashMessageAction, RemoveFlashMessageAction} from '../../actions/flashmessage';
import type FlashMessage from '../../reducers/types/flashMessage.state';

const store = getStore();

export function getFlashMessages(): FlashMessage[] {
  return store.getState().flashMessages
}

export function addFlashMessage(flashMessage: FlashMessage) {
  store.dispatch(new AddFlashMessageAction(flashMessage));
}

export function removeFlashMessage(id: number) {
  store.dispatch(new RemoveFlashMessageAction(id));
}
