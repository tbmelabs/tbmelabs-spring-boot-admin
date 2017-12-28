'use strict';

import React, {Component} from 'react';
import {render} from 'react-dom';

import {createStore, applyMiddleware, compose} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import reducers from './reducers';

import {CookiesProvider} from 'react-cookie';

import App from './container/app';
import Signup from './container/app/Signup';

const store = createStore(
  reducers,
  compose(
    applyMiddleware(thunk),
    window.devToolsExtension ? window.devToolsExtension() : f => f
  )
);

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