'use strict';

import axios, {CancelToken} from 'axios';

var cancelPassword;

export default function validatePassword(password, errors, callback) {
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