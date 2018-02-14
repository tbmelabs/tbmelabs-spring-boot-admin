'use strict';

import axios from 'axios';

export default function signin(data) {
  var formData = new FormData();
  formData.append('username', data.username);
  formData.append('password', data.password);

  return axios.post('signin', formData, {
    headers: {
      'no-redirect': ''
    }
  });
}