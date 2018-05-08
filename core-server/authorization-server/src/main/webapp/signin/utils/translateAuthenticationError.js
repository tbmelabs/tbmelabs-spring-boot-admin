// @flow
'use strict';

import getStore from '../getStore';

import {getTexts} from '../state/selectors/language';

const ACCOUNT_DISABLED_EXCEPTION = 'Authentication Failed: User is disabled';
const ACCOUNT_LOCKED_EXCEPTION = 'Authentication Failed: User account is locked';

export default (message: string) => {
  const errorTexts = getTexts(getStore().getState())['signin'].errors;

  switch (message) {
    case ACCOUNT_DISABLED_EXCEPTION:
      return errorTexts.account_disabled_text;
    case ACCOUNT_LOCKED_EXCEPTION:
      return errorTexts.account_locked_text;
    default:
      return errorTexts.bad_credentials_text;
  }
}
