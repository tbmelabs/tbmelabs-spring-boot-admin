'use strict';

import axios from 'axios';

export function loadScopesForClientId(clientId) {
  return axios.get('confirm_access_scopes?client_id=' + clientId);
}