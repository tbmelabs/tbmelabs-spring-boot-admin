// @flow
'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import getStore from './getStore.js';

import {CookiesProvider} from 'react-cookie';

import App from './container/app';
import Signin from './container/app/Signin';

require('../common/styles/tbme-tv.css');

render(
    <Provider store={getStore()}>
      <CookiesProvider>
        <App>
          <Signin/>
        </App>
      </CookiesProvider>
    </Provider>
    , (document.getElementById('app'): any));
