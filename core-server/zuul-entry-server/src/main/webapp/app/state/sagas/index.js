// @flow

import {
  requestAuthenticationSaga,
  signinUserSaga,
  signoutUserSaga
} from './authentication';
import {launchApplicationSaga} from './application';

export default [launchApplicationSaga, requestAuthenticationSaga,
  signinUserSaga, signoutUserSaga];
