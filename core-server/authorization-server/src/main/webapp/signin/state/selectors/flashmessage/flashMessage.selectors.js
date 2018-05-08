// @flow

import type {ApplicationState} from '../../reducers';

import {type FlashMessageState} from '../../reducers/flashMessage.reducer';

export function getFlashMessages(state: ApplicationState): FlashMessageState {
  return state.flashMessages;
}
