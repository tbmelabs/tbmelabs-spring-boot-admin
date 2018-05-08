// @flow

import type {ApplicationState} from '../../reducers';

export function getTexts(state: ApplicationState): any {
  return state.language.texts;
}
