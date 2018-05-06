// @flow
'use strict';

import axios from 'axios';

export default (data: { username: string, password: string }) => {
  var formData = new FormData();
  formData.append('username', data.username);
  formData.append('password', data.password);

  return axios.post('signin', formData, {
    headers: {
      'no-redirect': ''
    }
  });
}
