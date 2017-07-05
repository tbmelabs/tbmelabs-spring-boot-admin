'use strict';

import axios from 'axios';

export function requestPasswordReset(data) {
  return dispatch => {
    return axios.post('/login/reset-password', {email: data.email});
  }
}