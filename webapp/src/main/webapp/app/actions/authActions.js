'use strict';

import axios from 'axios';

import {setAuthorizationToken} from '../utils/setAuthorizationToken';

import {SET_CURRENT_USER} from './types';

export function setCurrentUser(user) {
  return {
    type: SET_CURRENT_USER,
    user
  };
}

export function login(data) {
  var formData = new FormData();
  formData.append('username', data.username);
  formData.append('password', data.password);

  return dispatch => {
    return axios.post('/login', formData)
      .then(response => {
        const token = response.data;
        localStorage.setItem('auth_token', token);
        setAuthorizationToken(token);

        dispatch => {
          axios.get('/profile').then(response => {
            setCurrentUser(response.data);
          });
        }
      });
  }
}

export function logout() {
  return dispatch => {
    localStorage.removeItem('auth_token');
    setAuthorizationToken(false);
    dispatch(setCurrentUser({}));
  }
}