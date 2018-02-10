'use strict';

import React, {Component} from 'react';

import GoodbyeMessage from './GoodbyeMessage';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component {
  render() {
    return (
      <div className='container'>
        <GoodbyeMessage/>
        <FlashMessageList/>
        {this.props.children}
      </div>
    );
  }
}

export default App;