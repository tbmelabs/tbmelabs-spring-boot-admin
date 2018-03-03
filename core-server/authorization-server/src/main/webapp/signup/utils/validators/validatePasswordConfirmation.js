// @flow
'use strict';

import axios, {CancelToken} from 'axios';

var cancelConfirmation;

export default function validatePasswordConfirmation(password: string, confirmation: string, errors: any, callback: function) {
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