// @flow

import {parse} from 'query-string';

import getStore from '../../getStore';

import {getTexts} from '../../state/selectors/language';
import {addFlashMessage} from '../../state/queries/flashmessage';

const texts = getTexts(getStore().getState()).app.flash_messages.logout;

export default function addGoodbyeMessageIfRequested() {
  if (parse(window.location.search.substr(1)).goodbye !== undefined) {
    addFlashMessage({
      type: 'success',
      title: texts.logout_succeed_alert_title,
      text: texts.logout_succeed_alert_text
    });
  }
}
