// @flow

import {put, takeLatest} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import getStore from '../../../getStore';

import {getTexts} from '../../selectors/language';
import {
  REQUEST_CLIENT_SCOPES,
  REQUEST_CLIENT_SCOPES_FAILED,
  requestClientScopesFailedAction,
  setClientScopesAction
} from '../../actions/client';
import {addFlashMessageAction} from '../../actions/flashmessage';

function* requestClientScopes(clientId: string) {
  try {
    const response: AxiosResponse = yield  axios.get('confirm_access_scopes?client_id='
        + clientId);

    yield put(setClientScopesAction(response.data));
  } catch (error) {
    yield put(requestClientScopesFailedAction());
  }
}

export function* requestClientScopesSaga(): Generator<any, void, any> {
  yield takeLatest(REQUEST_CLIENT_SCOPES, requestClientScopes);
}

function* requestClientScopesFailed() {
  const texts = getTexts(getStore().getState()).authorize;

  yield put(addFlashMessageAction({
    type: 'danger',
    title: texts.scope_fetch_failed_alert_title,
    text: texts.scope_fetch_failed_alert_text
  }));
}

export function* requestClientScopesFailedSaga(): Generator<any, void, any> {
  yield takeLatest(REQUEST_CLIENT_SCOPES_FAILED, requestClientScopesFailed);
}