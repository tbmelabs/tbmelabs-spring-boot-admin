// @flow
'use strict';

import React, {Component} from 'react';

import {
  requestAuthentication,
  signinUser
} from '../../state/queries/authentication';

class App extends Component<App.propTypes> {
  componentWillMount() {
    requestAuthentication();
  }

  render() {
    return (
        <div className="intro-container">
          <div className="intro-body d-flex align-items-center">
            <div className="container justify-content-center text-center">
              <h1 className="pb-4">TBME TV</h1>

              <button onClick={signinUser} className="btn btn-transparent">
                <span>Sign In</span>
              </button>
            </div>
          </div>
        </div>
    );
  }
}

export default App;
