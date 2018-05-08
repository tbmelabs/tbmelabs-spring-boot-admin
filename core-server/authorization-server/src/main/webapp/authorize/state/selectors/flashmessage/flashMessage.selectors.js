// @flow

import type {ApplicationState} from '../../reducers';

import type {FlashMessageType} from '../../reducers/flashMessage.reducer';
import {type FlashMessageState} from '../../reducers/flashMessage.reducer';

export function getFlashMessages(state: ApplicationState): FlashMessageState {
  return state.flashMessages;
}

export function getFlashMessageByUid(state: ApplicationState,
    uid: string): FlashMessageType | null {
  const optionalFlashMessage = getFlashMessages(state).filter(
      (flashMessage: FlashMessageType) => flashMessage.uid === uid);

  return optionalFlashMessage.length !== 0 ? optionalFlashMessage[0] : null;
}
