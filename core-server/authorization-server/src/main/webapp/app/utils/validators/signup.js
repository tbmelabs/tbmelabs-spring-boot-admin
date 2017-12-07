'use strict';

import axios from 'axios';

export function validateUsername(username, errors, callback) {
  if (username == undefined || username == '') {
    delete errors.username;
    return;
  }

  axios.post('/signup/does-username-match-format', {username: username}, {
    auth: {
      username: require('../../config/config.json').clientId
    }
  }).then(
    response => {
      axios.post('/signup/is-username-unique', {username: username}).then(
        response => {
          delete errors.username;
          callback(errors);
        }, error => {
          errors.username = error.response.data.message;
          callback(errors);
        }
      );
    }, error => {
      errors.username = error.response.data.message;
      callback(errors);
    }
  );
}

export function validateEmail(email, errors, callback) {
  if (email == undefined || email == '') {
    delete errors.email;
    return;
  }

  axios.post('/signup/is-email', {email: email}).then(
    response => {
      axios.post('/signup/is-email-unique', {email: email}).then(
        response => {
          delete errors.email;
          callback(errors);
        }, error => {
          errors.email = error.response.data.message;
          callback(errors);
        }
      );
    }, error => {
      errors.email = error.response.data.message;
      callback(errors);
    }
  );
}

export function validatePassword(password, errors, callback) {
  if (password == undefined || password == '') {
    delete errors.password;
    return;
  }

  axios.post('/signup/does-password-match-format', {password: password}).then(
    response => {
      delete errors.password;
      callback(errors);
    }, error => {
      errors.password = error.response.data.message;
      callback(errors);
    }
  );
}

export function validatePasswordConfirmation(password, confirmation, errors, callback) {
  if (password == undefined || password == '' || confirmation == undefined || confirmation == '') {
    delete errors.confirmation;
    return;
  }

  axios.post('/signup/do-passwords-match', {password: password, confirmation: confirmation}).then(
    response => {
      delete errors.confirmation;
      callback(errors);
    }, error => {
      errors.confirmation = error.response.data.message;
      callback(errors);
    }
  );
}