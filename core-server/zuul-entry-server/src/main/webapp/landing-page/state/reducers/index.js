// @flow

import {combineReducers} from 'redux';

import authentication, {type AuthenticationState} from './authentication.reducer';
import flashMessages, {type FlashMessageState} from './flashMessage.reducer';
import language, {type LanguageState} from './language.reducer';

export type ApplicationState = {
  authentication: AuthenticationState,
  flashMessages: FlashMessageState,
  language: LanguageState
}

export default combineReducers({
  authentication, flashMessages, language
});
