'use strict';

import axios from 'axios';

export function travelTo(location) {
  return dispatch => {
    return axios.get(location);
  }
}