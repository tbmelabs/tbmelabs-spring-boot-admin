'use strict';

import {combineReducers} from 'redux';

import auth from './auth';
import language from './language';

export default combineReducers({
  auth, language
});