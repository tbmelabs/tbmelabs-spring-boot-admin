// @flow
'use strict';

import axios from 'axios';

export default function loadScopesForClientId(clientId: string) {
  return axios.get('confirm_access_scopes?client_id=' + clientId);
}