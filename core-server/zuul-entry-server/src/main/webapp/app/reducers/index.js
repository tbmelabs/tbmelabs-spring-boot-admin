// @flow

import {combineReducers} from 'redux';

import authentication from './authentication.reducer';
import flashMessages from './flashMessage.reducer';
import language from './language.reducer';

export default combineReducers({
  authentication, flashMessages, language
});