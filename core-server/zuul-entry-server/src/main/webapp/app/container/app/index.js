// @flow
'use strict';

import React, {Component} from 'react';

import {requestAuthentication} from "../../state/queries/authentication";

import Navbar from './Navbar';
import FlashMessageList from '../common/FlashMessageList';

class App extends Component<App.propTypes> {
  componentWillMount() {
    requestAuthentication();
  }

  render() {
    return (
        <div>
          <Navbar/>

          <div className='container'>
            <FlashMessageList/>

            {this.props.children}
          </div>
        </div>
    );
  }
}

export default App;
