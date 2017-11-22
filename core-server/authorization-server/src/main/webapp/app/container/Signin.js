'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import queryString from 'query-string';

import {authenticateUser} from '../actions/authActions';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';

import UsernamePasswordLoginForm from '../components/signin/UsernamePasswordLoginForm';

require('bootstrap/dist/css/bootstrap.css');
require('../styles/signin.css');

class Signin extends Component {
  componentDidMount() {
    document.title = 'TBME Labs | Sign In';
  }

  render() {
    const {redirect} = queryString.parse(this.context.router.route.location.search);
    const {authenticateUser} = this.props.actions;

    return (
      <div className='container'>
        <Jumbotron>
          <h1>TBME Labs - Sign In</h1>
          <p>Sign in to your TBME Labs account using your username and password.</p>
        </Jumbotron>

        <div className='signin-form'>
          <UsernamePasswordLoginForm authenticateUser={authenticateUser} redirectUrl={redirect}/>
        </div>
      </div>
    );
  }
}

Signin.propTypes = {
  actions: PropTypes.object.isRequired
}

Signin.contextTypes = {
  router: PropTypes.object.isRequired
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      authenticateUser: bindActionCreators(authenticateUser, dispatch)
    }
  }
}

export default connect(null, mapDispatchToProps)(Signin);