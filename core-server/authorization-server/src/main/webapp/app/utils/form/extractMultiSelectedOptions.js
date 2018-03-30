// @flow
'use strict';

import filter from 'lodash/filter';
import map from 'lodash/map';

export default function (select: HTMLSelectElement, allElements: any[], callback: (selectedElements: any[]) => void) {
  callback(filter(allElements, (element) => map(select.selectedOptions, (option) => Number(option.value)).indexOf(element.id) !== -1));
}