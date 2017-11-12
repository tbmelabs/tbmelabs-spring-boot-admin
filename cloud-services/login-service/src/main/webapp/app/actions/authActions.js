'use strict';

import axios from 'axios';

import {SET_ACCESS_TOKEN} from './types';

export function setAccessToken(token) {
  return {
    type: SET_ACCESS_TOKEN,
    token
  };
}

export function authenticateUser(data) {
  var formData = new FormData();
  formData.append('username', data.username);
  formData.append('password', data.password);

  return dispatch => {
    return axios.post('/signin', formData)
      .then(response => {
        axios.defaults.headers.common['Authorization'] = response.data.token_type + response.data.access_token;

        dispatch(setAccessToken(response.data.access_token));
      });
  }
}

export function logout() {
  return dispatch => {
    axios.defaults.headers.common['Authorization'] = '';

    dispatch(setAccessToken(''));
  }
}