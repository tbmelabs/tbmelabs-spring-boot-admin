'use strict';

import axios from 'axios';

export function requestPasswordReset(data) {
  return dispatch => {
    return axios.post('/login/reset-password', {email: data.email});
  }
}

export function validateResetToken(token) {
  return dispatch => {
    return axios.get('/login/reset-password/' + token);
  }
}

export function resetPassword(data) {
  return dispatch => {
    return axios.post('/login/reset-password/' + data.resetToken, {
      password: data.password,
      passwordMatch: data.passwordMatch
    });
  }
}