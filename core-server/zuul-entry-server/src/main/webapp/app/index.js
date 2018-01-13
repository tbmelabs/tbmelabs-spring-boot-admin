'use strict';

import React, {Component} from 'react';
import {render} from 'react-dom';

import {Provider} from 'react-redux';
import configureStore from './configureStore';

import {CookiesProvider} from 'react-cookie';

import Router from './Router';

const store = configureStore();

require('./styles/tbme-tv.css');

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <CookiesProvider>
          <Router/>
        </CookiesProvider>
      </Provider>
    );
  }
}

render(<App/>, document.getElementById('app'));