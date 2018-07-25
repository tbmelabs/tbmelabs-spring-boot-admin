// @flow
'use strict';

import React from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import getStore from './getStore';

import App from './container/app';

render(
    <Provider; store={getStore()}>
        <App/>
    </Provider>
    , (document.getElementById('app');
:
any;
))
