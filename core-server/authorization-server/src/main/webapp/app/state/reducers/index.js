// @flow

import {combineReducers} from 'redux';

import clients, {type ClientState} from './client.reducer';
import flashMessages, {type FlashMessageState} from './flashMessage.reducer';
import language, {type LanguageState} from './language.reducer';
import profile, {type ProfileState} from './profile.reducer';

export type ApplicationState = {
  clients: ClientState,
  flashMessages: FlashMessageState,
  language: LanguageState,
  profile: ProfileState
};

export default combineReducers({
  clients, flashMessages, language, profile
});
