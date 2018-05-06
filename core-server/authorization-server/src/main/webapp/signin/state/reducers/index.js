// @flow

import {combineReducers} from 'redux';

import flashMessages from './flashMessage.reducer';
import language from './language.reducer';

export default combineReducers({
  flashMessages, language
});
