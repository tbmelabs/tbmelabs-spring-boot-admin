// @flow

import getStore from '../../getStore';

const store = getStore();

export function getTexts(component: string): any {
  return store.getState().language.texts[component];
}