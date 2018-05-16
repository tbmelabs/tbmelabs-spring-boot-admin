// @flow

import {parse} from 'query-string';

import getStore from '../../getStore';

import {getTexts} from '../../state/selectors/language';
import {addFlashMessage} from '../../state/queries/flashmessage';

const texts = getTexts(getStore().getState()).app.flash_messages.confirmation;

export default function addConfirmationMessageIfRequested() {
  if (parse(window.location.search.substr(1)).confirmation_failed
      !== undefined) {
    addFlashMessage({
      type: 'danger',
      title: texts.confirmation_failed_alert_title,
      text: texts.confirmation_failed_alert_text
    });
  } else if (parse(window.location.search.substr(1)).confirmation_succeed
      !== undefined) {
    addFlashMessage({
      type: 'success',
      title: texts.confirmation_succeed_alert_title,
      text: texts.confirmation_succeed_alert_text
    });
  }
}
