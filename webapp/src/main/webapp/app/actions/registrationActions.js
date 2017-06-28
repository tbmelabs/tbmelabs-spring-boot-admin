'use strict';

import axios from 'axios';

export function isUsernameUnique(username) {
  return dispatch => {
    axios.post('/register/check/is-username-unique', {username: username});
  }
}

export function isEmailUnique(email) {
  return dispatch => {
    axios.post('/register/check/is-email-unique', {email: email});
  }
}

export function doesPasswordMatchFormat(password) {
  return dispatch => {
    axios.post('/register/check/does-password-match-format', {password: password});
  }
}

export function doPasswordsMatch(password, passwordMatch) {
  return disaptch => {
    axios.post('/register/check/do-passwords-match', {
      password: this.state.password,
      passwordMatch: this.state.passwordMatch
    });
  }
}

export function registerUser(data) {
  return dispatch => {
    axios.post('/register', {
      username: data.username,
      email: data.email,
      password: data.password,
      passwordMatch: data.passwordMatch
    });
  }
}