// @flow

import {combineReducers} from 'redux';

import authorities, {type AuthorityState} from './authority.reducer';
import clients, {type ClientState} from './client.reducer';
import flashMessages, {type FlashMessageState} from './flashMessage.reducer';
import grantTypes, {type GrantTypeState} from './grantType.reducer';
import language, {type LanguageState} from './language.reducer';
import profile, {type ProfileState} from './profile.reducer';
import scopes, {type ScopeState} from './scope.reducer';

export type ApplicationState = {
  authorities: AuthorityState,
  clients: ClientState,
  flashMessages: FlashMessageState,
  grantTypes: GrantTypeState,
  language: LanguageState,
  profile: ProfileState,
  scopes: ScopeState
};

export default combineReducers({
  authorities, clients, flashMessages, grantTypes, language, profile, scopes
});
