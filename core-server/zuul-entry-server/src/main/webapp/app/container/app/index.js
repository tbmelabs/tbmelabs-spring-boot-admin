// @flow
'use strict';

import React, {Component} from 'react';

import Navbar from './Navbar';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
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