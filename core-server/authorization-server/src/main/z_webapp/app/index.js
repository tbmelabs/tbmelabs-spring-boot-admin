'use strict';

import React, {Component} from 'react';
import {render} from 'react-dom';

import {createStore, applyMiddleware, compose} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import reducers from './reducers';

import {CookiesProvider} from 'react-cookie';

import Router from './Router';

const store = createStore(
  reducers,
  compose(
    applyMiddleware(thunk),
    window.devToolsExtension ? window.devToolsExtension() : f => f
  )
);

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