'use strict';

import {combineReducers} from 'redux';

import flashMessages from './flashMessages';
import language from './language';

export default combineReducers({
  flashMessages, language
});