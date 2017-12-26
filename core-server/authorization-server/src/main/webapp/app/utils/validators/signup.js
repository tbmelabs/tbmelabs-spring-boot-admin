'use strict';

import axios, {CancelToken} from 'axios';

var cancelUsername;
var cancelEmail;
var cancelPassword;
var cancelConfirmation;

export function validateUsername(username, errors, callback) {
  if (username == undefined || username == '') {
    delete errors.username;
    return;
  }

  if (cancelUsername != null) {
    cancelUsername();
  }

  axios.post('signup/does-username-match-format', {username: username}, {
    cancelToken: new CancelToken(function executor(c) {
      cancelUsername = c;
    })
  }).then(
    response => {
      axios.post('signup/is-username-unique', {username: username}, {
        cancelToken: new CancelToken(function executor(c) {
          cancelUsername = c;
        })
      }).then(
        response => {
          delete errors.username;
          callback(errors);
        }, error => {
          if (!axios.isCancel(error)) {
            errors.username = error.response.data.message;
            callback(errors);
          }
        }
      );
    }, error => {
      if (!axios.isCancel(error)) {
        errors.username = error.response.data.message;
        callback(errors);
      }
    }
  );
}

export function validateEmail(email, errors, callback) {
  if (email == undefined || email == '') {
    delete errors.email;
    return;
  }

  if (cancelEmail != null) {
    cancelEmail();
  }

  axios.post('signup/is-email', {email: email}, {
    cancelToken: new CancelToken(function executor(c) {
      cancelEmail = c;
    })
  }).then(
    response => {
      axios.post('signup/is-email-unique', {email: email}, {
        cancelToken: new CancelToken(function executor(c) {
          cancelEmail = c;
        })
      }).then(
        response => {
          delete errors.email;
          callback(errors);
        }, error => {
          if (!axios.isCancel(error)) {
            errors.email = error.response.data.message;
            callback(errors);
          }
        }
      );
    }, error => {
      if (!axios.isCancel(error)) {
        errors.email = error.response.data.message;
        callback(errors);
      }
    }
  );
}

export function validatePassword(password, errors, callback) {
  if (password == undefined || password == '') {
    delete errors.password;
    return;
  }

  if (cancelPassword != null) {
    cancelPassword();
  }

  axios.post('signup/does-password-match-format', {password: password}, {
    cancelToken: new CancelToken(function executor(c) {
      cancelPassword = c;
    })
  }).then(
    response => {
      delete errors.password;
      callback(errors);
    }, error => {
      if (!axios.isCancel(error)) {
        errors.password = error.response.data.message;
        callback(errors);
      }
    }
  );
}

export function validatePasswordConfirmation(password, confirmation, errors, callback) {
  if (password == undefined || password == '' || confirmation == undefined || confirmation == '') {
    delete errors.confirmation;
    return;
  }

  if (cancelConfirmation != null) {
    cancelConfirmation();
  }

  axios.post('signup/do-passwords-match', {password: password, confirmation: confirmation}, {
    cancelToken: new CancelToken(function executor(c) {
      cancelConfirmation = c;
    })
  }).then(
    response => {
      delete errors.confirmation;
      callback(errors);
    }, error => {
      if (!axios.isCancel(error)) {
        errors.confirmation = error.response.data.message;
        callback(errors);
      }
    }
  );
}