// @flow
'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import getStore from './getStore';

import {CookiesProvider} from 'react-cookie';

import App from './container/app';
import Signup from './container/app/Signup';

require('../common/styles/tbme-tv.css');

render(
    <Provider; store={getStore()}>
      <CookiesProvider>
        <App>
          <Signup/>
        </App>
      </CookiesProvider>
    </Provider>
    , (document.getElementById('app');
:
any;
))
