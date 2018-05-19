// @flow

import getStore from '../../../getStore';

import {getTexts} from '../../selectors/language';
import {addFlashMessage} from './flashMessage.queries';

export function addInvalidRequestFlashMessage(error: string) {
  const texts = getTexts(getStore().getState()).app;

  addFlashMessage({
    type: 'danger',
    title: texts.invalid_request_alert_title,
    text: error
  });
};

export function addInsufficientPermissionFlashMessage() {
  const texts = getTexts(getStore().getState()).app;

  addFlashMessage({
    type: 'danger',
    title: texts.access_denied_alert_title,
    text: texts.access_denied_alert_text
  });
};

export function addServerNotRespondingFlashMessage() {
  const texts = getTexts(getStore().getState()).app;

  addFlashMessage({
    type: 'danger',
    title: texts.server_not_responding_alert_title,
    text: texts.server_not_responding_alert_text
  });
};
