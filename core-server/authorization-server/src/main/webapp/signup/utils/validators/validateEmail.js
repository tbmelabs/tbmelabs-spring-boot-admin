'use strict';

import axios, {CancelToken} from 'axios';

var cancelEmail;

export default function validateEmail(email, errors, callback) {
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