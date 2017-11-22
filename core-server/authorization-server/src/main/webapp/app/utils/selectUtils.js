'use strict';

import axios from 'axios';

export function loadAvailableApplications() {
  return axios.get('public/known-applications');
}