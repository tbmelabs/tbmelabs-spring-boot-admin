'use strict';

import axios from 'axios';

import {login} from './authActions'

export function isUsernameUnique(username) {
  return dispatch => {
    return axios.post('/register/check/is-username-unique', {username: username});
  }
}

export function isEmailUnique(email) {
  return dispatch => {
    return axios.post('/register/check/is-email-unique', {email: email});
  }
}

export function doesPasswordMatchFormat(password) {
  return dispatch => {
    return axios.post('/register/check/does-password-match-format', {password: password});
  }
}

export function doPasswordsMatch(password, passwordMatch) {
  return dispatch => {
    return axios.post('/register/check/do-passwords-match', {
      password: password,
      passwordMatch: passwordMatch
    });
  }
}

export function registerUser(data) {
  return dispatch => {
    return axios.post('/register', {
      username: data.username,
      email: data.email,
      password: data.password,
      passwordMatch: data.passwordMatch
    }).then(
      response => {
        dispatch => login(data);
      }
    );
  }
}