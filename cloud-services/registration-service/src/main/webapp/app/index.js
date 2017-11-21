'use strict';

import React from 'react';
import {render} from 'react-dom';

import {CookiesProvider} from 'react-cookie';

import {BrowserRouter} from 'react-router-dom';
import {Route, Switch} from 'react-router-dom';

import Login from './container/Login';

require('./styles/tbme-tv.css');

class App extends React.Component {
  render() {
    return (
      <CookiesProvider>
        <BrowserRouter>
          <Switch>
            <Route path="/" component={Login}/>
          </Switch>
        </BrowserRouter>
      </CookiesProvider>
    );
  }
}

render(<App/>, document.getElementById('app'));

document.getElementById('enable-script-div').remove();