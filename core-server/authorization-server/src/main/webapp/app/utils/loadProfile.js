// @flow
'use strict';

import axios from 'axios';

export default function () {
  return axios.get('profile');
}