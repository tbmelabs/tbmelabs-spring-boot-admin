'use strict';

import axios from 'axios';

export function loadAvailableApplications() {
  return axios.get('api/known-applications');
}