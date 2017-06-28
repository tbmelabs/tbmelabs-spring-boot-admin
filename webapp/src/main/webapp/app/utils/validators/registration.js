'use strict';

import Validator from 'validator';
import isEmtpy from 'lodash/isEmpty';

export default function validateInput(data) {
  let errors = {};

  if (Validator.isNull(data.username)) {
    errors.username = 'This field is required';
  }

  if (Validator.isNull(data.password)) {
    errors.password = 'This field is required';
  }

  if (data.password !== data.passwordMatch) {
    errors.passwordMatch = 'The passwords have to match';
  }

  return {
    errors,
    isValid: isEmtpy(errors)
  };
}