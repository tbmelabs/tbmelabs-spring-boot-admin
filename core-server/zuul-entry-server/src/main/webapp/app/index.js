// @flow
'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import getStore from './getStore';

import {CookiesProvider} from 'react-cookie';

import Router from './Router';

require('./styles/tbme-tv.css');

render(
    <Provider store={getStore()}>
      <CookiesProvider>
        <Router/>
      </CookiesProvider>
    </Provider>
    , (document.getElementById('app'): any));
