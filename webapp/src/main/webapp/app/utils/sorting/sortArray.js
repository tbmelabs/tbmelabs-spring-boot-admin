'use strict';

import {SORT_ASC, SORT_DESC} from './types';

export default function sortArray(array, key, order) {
  if (order !== SORT_ASC && order !== SORT_DESC) {
    return array;
  } else if (order === SORT_ASC) {
    return array.sort((a, b) => {
      return (a[key] > b[key]) ? 1 : ((a[key] > b[key]) ? -1 : 0)
    });
  } else if (order === SORT_DESC) {
    return array.sort((a, b) => {
      return (a[key] < b[key]) ? 1 : ((a[key] < b[key]) ? -1 : 0)
    });
  }
}