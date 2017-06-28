'use strict';

import {combineReducers} from 'redux';

import flashMessage from './flashMessage';
import auth from './auth';

const reducers = combineReducers({
  flashMessage,
  auth
});

export default reducers;