// @flow

import {combineReducers} from 'redux';

import flashMessages, {type FlashMessageState} from './flashMessage.reducer';
import language, {type LanguageState} from './language.reducer';
import client, {type ClientState} from './client.reducer';

export type ApplicationState = {
  client: ClientState;
  flashMessages: FlashMessageState;
  language: LanguageState;
};

export default combineReducers({
  client, flashMessages, language
});
