// @flow

import type {ApplicationState, FlashMessageState} from '../../reducers';

export function getFlashMessages(state: ApplicationState): FlashMessageState {
  return state.flashMessages;
}
