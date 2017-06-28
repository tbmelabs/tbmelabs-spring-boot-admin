'use strict';

import React from 'react';

import Navigation from './Navigation';
import FlashMessageList from '../flashmessage/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends React.Component {
  render() {
    return (
      <div class='container'>
        <Navigation/>
        <FlashMessageList/>
        {this.props.children}
      </div>
    );
  }
}

export default App;