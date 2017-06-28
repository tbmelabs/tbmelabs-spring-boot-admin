'use strict';

import React from 'react';

import auth from '../utils/requireAuth';

require('bootstrap/dist/css/bootstrap.css');

class Home extends React.Component {
  constructor(props) {
    super(props);

    this.handleLogout = this.handleLogout.bind(this);
  }

  componentWillMount() {
    if (!auth.isLoggedIn()) {
      this.props.history.push('/login');
    }
  }

  handleLogout() {
    var self = this;

    auth.logout(function () {
      self.props.history.push('/');
    })
  }

  render() {
    return (
      <home>
        <h1>Welcome to TBME Labs TV</h1>
      </home>
    );
  }
}

export default Home;