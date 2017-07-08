'use strict';

import validator from 'validator';
import isEmpty from 'validate.io-empty';

export default function validateInput(data) {
  let errors = {};

  if (data.errors.email !== undefined && !isEmpty(data.errors.email)) {
    errors.email = data.errors.email;
  } else if (validator.isEmpty(data.email)) {
    errors.email = 'This field is required';
  } else if (!validator.isEmail(data.email)) {
    errors.email = 'Please enter a valid email address';
  }

  return {
    errors: errors,
    isValid: isEmpty(errors)
  };
}