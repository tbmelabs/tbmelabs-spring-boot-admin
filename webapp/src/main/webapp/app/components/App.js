'use strict';

import React from 'react';

import Navigation from './Navigation';
import FlashMessageList from '../flashmessage/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

// TODO: props.messages is undefined
class App extends React.Component {
  render() {
    return (
      <div className='container'>
        <Navigation/>
        {this.props.children}
      </div>
    );
  }
}

export default App;