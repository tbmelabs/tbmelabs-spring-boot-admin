// @flow

import {put, takeEvery} from 'redux-saga/effects';

import axios, {AxiosResponse} from 'axios';

import getStore from '../../../getStore';

import {REST_API_BASE_PATH} from '../../../config';

import {type clientType} from '../../../../common/types/client.type';
import {
  DELETE_CLIENT,
  DELETE_CLIENT_SUCCEED,
  deleteClientSucceedAction,
  REQUEST_CLIENTS,
  requestClientsAction,
  SAVE_CLIENT,
  SAVE_CLIENT_SUCCEED,
  saveClientSucceedAction,
  setClientsAction,
  UPDATE_CLIENT,
  UPDATE_CLIENT_SUCCEED,
  updateClientSucceedAction
} from '../../actions/client';
import {addFlashMessageAction} from '../../actions/flashmessage';
import {getTexts} from '../../selectors/language';

function* requestClients() {
  const response: AxiosResponse = yield axios.get(
      `${REST_API_BASE_PATH}/clients`);
  if (response.status === 200) {
    yield put(setClientsAction(response.data));
  }
}

export function* requestClientsSaga(): Generator<any, void, any> {
  yield takeEvery(REQUEST_CLIENTS, requestClients);
}

function* saveClient(action: { type: string, payload: clientType }) {
  const response: AxiosResponse = yield axios.post(
      `${REST_API_BASE_PATH}/clients`, action.payload);
  if (response.status === 200) {
    yield put(saveClientSucceedAction());
  }
}

export function* saveClientSaga(): Generator<any, void, any> {
  yield takeEvery(SAVE_CLIENT, saveClient);
}

function* saveClientSucceed() {
  const texts = getTexts(getStore().getState()).clients.modal;

  yield put(addFlashMessageAction({
    type: 'success',
    title: texts.client_action_success_title,
    text: texts.client_added_text
  }));

  yield put(requestClientsAction());
}

export function* saveClientSucceedSaga(): Generator<any, void, any> {
  yield takeEvery(SAVE_CLIENT_SUCCEED, saveClientSucceed);
}

function* updateClient(action: { type: string, payload: clientType }) {
  const response: AxiosResponse = yield axios.put(
      `${REST_API_BASE_PATH}/clients`, action.payload);
  if (response.status === 200) {
    yield put(updateClientSucceedAction());
  }
}

export function* updateClientSaga(): Generator<any, void, any> {
  yield takeEvery(UPDATE_CLIENT, updateClient);
}

function* updateClientSucceed() {
  const texts = getTexts(getStore().getState()).clients.modal;

  yield put(addFlashMessageAction({
    type: 'success',
    title: texts.client_action_success_title,
    text: texts.client_updated_text
  }));

  yield put(requestClientsAction());
}

export function* updateClientSucceedSaga(): Generator<any, void, any> {
  yield takeEvery(UPDATE_CLIENT_SUCCEED, updateClientSucceed);
}

function* deleteClient(action: { type: string, payload: number }) {
  const response: AxiosResponse = yield axios.delete(
      `${REST_API_BASE_PATH}/clients/${action.payload}`);
  if (response.status === 200) {
    yield put(deleteClientSucceedAction());
  }
}

export function* deleteClientSaga(): Generator<any, void, any> {
  yield takeEvery(DELETE_CLIENT, deleteClient);
}

function* deleteClientSucceed() {
  const texts = getTexts(getStore().getState()).clients.modal;

  yield put(addFlashMessageAction({
    type: 'success',
    title: texts.client_action_success_title,
    text: texts.client_deleted_text
  }));

  yield put(requestClientsAction());
}

export function* deleteClientSucceedSaga(): Generator<any, void, any> {
  yield takeEvery(DELETE_CLIENT_SUCCEED, deleteClientSucceed);
}
