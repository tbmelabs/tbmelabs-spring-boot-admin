'use strict';

import React from 'react';

import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends React.Component {
  render() {
    return (
      <div className='container'>
        <FlashMessageList/>
        {this.props.children}
      </div>
    );
  }
}

export default App;