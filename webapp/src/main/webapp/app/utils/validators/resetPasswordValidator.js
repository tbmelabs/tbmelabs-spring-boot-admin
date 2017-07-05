'use strict';

import validator from 'validator';
import isEmpty from 'validate.io-empty';

export default function validateInput(data) {
  let errors = {};

  if (!validator.isEmpty(data.errors.password)) {
    errors.password = data.errors.password;
  } else if (validator.isEmpty(data.password)) {
    errors.password = 'This field is required';
  }

  if (!validator.isEmpty(data.errors.passwordMatch)) {
    errors.passwordMatch = data.errors.password;
  } else if (validator.isEmpty(data.passwordMatch)) {
    errors.passwordMatch = 'This field is required';
  } else if (data.password !== data.passwordMatch) {
    errors.passwordMatch = 'The passwords do not match';
  }

  return {
    errors: errors,
    isValid: isEmpty(errors)
  };
}