'use strict';

import {combineReducers} from 'redux';

import auth from './auth';
import flashMessages from './flashMessages';

const reducers = combineReducers({
  auth,
  flashMessages
});

export default reducers;