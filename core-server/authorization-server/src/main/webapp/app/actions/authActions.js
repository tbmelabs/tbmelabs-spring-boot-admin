'use strict';

import axios from 'axios';

export function authenticateUser(data) {
  var formData = new FormData();
  formData.append('username', data.username);
  formData.append('password', data.password);

  return axios.post('signin', formData);
}

export function logout() {
  return axios.post('logout');
}