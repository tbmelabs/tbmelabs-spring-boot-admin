'use strict';

import axios from '../utils/axiosUtils';

import {validateUsername, validateEmail, validatePassword, validatePasswordConfirmation} from '../utils/validators/signup';

export function validateSignupForm(control, data, callback) {
  const {username, email, password, confirmation, errors} = data;

  delete errors.form;

  switch (control) {
    case 'username':
      validateUsername(username, errors, callback);
      break
    case 'email':
      validateEmail(email, errors, callback);
      break
    case 'password':
      validatePassword(password, errors, callback);
      validatePasswordConfirmation(password, confirmation, errors, callback);
      break
    case 'confirmation':
      validatePasswordConfirmation(password, confirmation, errors, callback);
      break
    default:
      validateUsername(username, errors, errors => {
        validateEmail(email, errors, errors => {
          validatePassword(password, errors, errors => {
            validatePasswordConfirmation(password, confirmation, errors, callback);
          });
        });
      });
  }
}

export function signupUser(data) {
  const {username, email, password, confirmation} = data;

  return axios.post('signup', {
    username: username,
    email: email,
    password: password,
    confirmation: confirmation
  });
}