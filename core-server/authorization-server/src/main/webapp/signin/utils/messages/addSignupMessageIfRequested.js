// @flow

import {parse} from 'query-string';

import getStore from '../../getStore';

import {getTexts} from '../../../signup/state/selectors/language';
import {addFlashMessage} from '../../state/queries/flashmessage';

const texts = getTexts(getStore().getState()).app.flash_messages.signup;

export default function addSignupMessageIfRequested() {
  if (parse(window.location.search.substr(1)).signup_succeed !== undefined) {
    addFlashMessage({
      type: 'success',
      title: texts.signup_succeed_alert_title,
      text: texts.signup_succeed_alert_text
    });
  }
}