'use strict';

import axios from 'axios';

export default function signupUser(data) {
  const {username, email, password, confirmation} = data;

  return axios.post('signup/do-signup', {
    username: username,
    email: email,
    password: password,
    confirmation: confirmation
  });
}