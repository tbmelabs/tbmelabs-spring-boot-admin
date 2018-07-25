// @flow

import {combineReducers} from 'redux';

import flashMessages, {type FlashMessageState} from './flashMessage.reducer';
import language, {type LanguageState} from './language.reducer';

export type; ApplicationState = {
  flashMessages: FlashMessageState;
  LanguageState;
}
export default combineReducers({
  flashMessages, language
});
