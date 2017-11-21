'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import queryString from 'query-string';

import {authenticateUser} from '../actions/authActions';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';

import UsernamePasswordLogin from '../components/login/UsernamePasswordLogin';

require('bootstrap/dist/css/bootstrap.css');
require('../styles/login.css');

class Login extends Component {
  render() {
    const {redirect} = queryString.parse(this.context.router.route.location.search);
    const {authenticateUser} = this.props.actions;

    return (
      <div className='container'>
        <Jumbotron>
          <h1>TBME Labs - Sign In</h1>
          <p>Sign in to your TBME Labs account using your username and password.</p>
        </Jumbotron>

        <div className='login-form'>
          <UsernamePasswordLogin authenticateUser={authenticateUser} redirectUrl={redirect}/>
        </div>
      </div>
    );
  }
}

Login.propTypes = {
  actions: PropTypes.object.isRequired
}

Login.contextTypes = {
  router: PropTypes.object.isRequired
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      authenticateUser: bindActionCreators(authenticateUser, dispatch)
    }
  }
}

export default connect(null, mapDispatchToProps)(Login);