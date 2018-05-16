// @flow

import {requestClientAuthoritiesSaga} from './authority';
import {requestClientsSaga} from './client';
import {requestClientGrantTypesSaga} from './grantType';
import {requestProfileSaga} from './profile';
import {requestClientScopesSaga} from './scope';

export default [requestClientAuthoritiesSaga, requestClientsSaga,
  requestClientGrantTypesSaga, requestProfileSaga, requestClientScopesSaga];
