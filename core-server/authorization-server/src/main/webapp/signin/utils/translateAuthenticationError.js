// @flow
'use strict';

import getStore from '../getStore.js';

const ACCOUNT_DISABLED_EXCEPTION = 'Authentication Failed: User is disabled';
const ACCOUNT_LOCKED_EXCEPTION = 'Authentication Failed: User account is locked';

export default (message: string) => {
  const errorTexts = getStore().getState().language.texts.signin.errors;

  switch (message) {
    case ACCOUNT_DISABLED_EXCEPTION:
      return errorTexts.account_disabled_text;
    case ACCOUNT_LOCKED_EXCEPTION:
      return errorTexts.account_locked_text;
  }

  return errorTexts.bad_credentials_text;
}
