// @flow

import {signoutUserSaga} from './authentication';
import {requestClientAuthoritiesSaga} from './authority';
import {
  requestClientsSaga,
  saveClientSaga,
  saveClientSucceedSaga
} from './client';
import {requestClientGrantTypesSaga} from './grantType';
import {requestProfileSaga} from './profile';
import {requestClientScopesSaga} from './scope';

export default [signoutUserSaga, requestClientAuthoritiesSaga,
  requestClientsSaga, saveClientSaga, saveClientSucceedSaga,
  requestClientGrantTypesSaga, requestProfileSaga, requestClientScopesSaga];
