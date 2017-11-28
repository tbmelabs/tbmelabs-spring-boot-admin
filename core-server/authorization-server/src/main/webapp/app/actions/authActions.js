'use strict';

import axios from 'axios';

import {SET_ACCESS_TOKEN} from './types';

import {setAuthorizationToken} from '../utils/authUtils';

export function setCurrentUser(data) {
  return {
    type: SET_ACCESS_TOKEN,
    data
  };
}

export function authenticateUser(data) {
  var formData = new FormData();
  formData.append('username', data.username);
  formData.append('password', data.password);
  formData.append('grant_type', 'password');

  return dispatch => {
    return axios.post('oauth/token', formData, {
      auth: {
        username: require('../config/config.json').clientId
      }
    }).then(
      response => {
        setAuthorizationToken(response.data.access_token);

        dispatch(setCurrentUser(response.data));
      }
    );
  };
}

export function logout() {
  return dispatch => {
    setAuthorizationToken();

    dispatch(setCurrentUser({}));
  }
}