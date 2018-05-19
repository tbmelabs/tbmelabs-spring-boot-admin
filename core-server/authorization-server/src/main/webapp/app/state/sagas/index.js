// @flow

import {signoutUserSaga} from './authentication';
import {requestClientAuthoritiesSaga} from './authority';
import {requestClientsSaga} from './client';
import {requestClientGrantTypesSaga} from './grantType';
import {requestProfileSaga} from './profile';
import {requestClientScopesSaga} from './scope';

export default [signoutUserSaga, requestClientAuthoritiesSaga,
  requestClientsSaga, requestClientGrantTypesSaga, requestProfileSaga,
  requestClientScopesSaga];
