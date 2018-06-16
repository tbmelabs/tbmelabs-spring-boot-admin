// @flow
'use strict';

import React, {Component} from 'react';

import {requestAuthentication} from "../../state/queries/authentication";

class App extends Component<App.propTypes> {
  componentWillMount() {
    requestAuthentication();
  }

  render() {
    return (
        <div>
          <h1>This will be the desktop application.</h1>
        </div>
    );
  }
}

export default App;
