// @flow

import getStore from '../../../getStore';

export function getTexts(component: string): any {
  return getStore().getState().language.texts[component];
}
