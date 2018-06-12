// @flow
'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import getStore from './getStore';

import Router from './Router';

require('./styles/tbme-tv.css');
require('bootstrap/dist/css/bootstrap.min.css');

render(
    <Provider store={getStore()}>
        <Router/>
    </Provider>
    , (document.getElementById('app'): any));
