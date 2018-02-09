'use strict';

import React, {Component} from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import configureStore from './configureStore';

import {CookiesProvider} from 'react-cookie';

import App from './container/app';
import Signup from './container/app/Signup';

const store = configureStore();

require('./styles/tbme-tv.css');

class ReactApplication extends Component {
  render() {
    return (
      <Provider store={store}>
        <CookiesProvider>
          <App>
            <Signup/>
          </App>
        </CookiesProvider>
      </Provider>
    );
  }
}

render(<ReactApplication/>, document.getElementById('app'));