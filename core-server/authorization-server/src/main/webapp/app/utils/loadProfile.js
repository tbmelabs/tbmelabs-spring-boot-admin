// @flow
'use strict';

import axios from 'axios';

export default function loadProfile() {
  return axios.get('profile');
}