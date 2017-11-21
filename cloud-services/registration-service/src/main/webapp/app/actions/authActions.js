'use strict';

import axios from 'axios';

export function authenticateUser(data, success, fail) {
  var formData = new FormData();
  formData.append('username', data.username);
  formData.append('password', data.password);

  return axios.post('oauth/token', formData)
    .then(response => {
      success(response);
    }, error => {
      fail(error);
    });
}

export function logout() {
  axios.defaults.headers.common['Authorization'] = '';
}