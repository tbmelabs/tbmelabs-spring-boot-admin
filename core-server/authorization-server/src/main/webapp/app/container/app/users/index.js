// @flow
'use strict';

import React, {Component} from 'react';

import {connect} from 'react-redux';

class Users extends Component<Users.propTypes> {
  componentWillMount() {
    console.log('Request users here..');
  }

  render() {
    return (<h1>This is gonna be a user management..</h1>);
  }
}

export default connect()(Users);
