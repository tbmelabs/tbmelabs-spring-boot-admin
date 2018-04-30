// @flow

import {requestAuthenticationSaga, signinUserSaga, signoutUserSaga} from './authentication.sagas';

export default [requestAuthenticationSaga, signinUserSaga, signoutUserSaga];