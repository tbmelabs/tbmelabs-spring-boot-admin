'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import queryString from 'query-string';

import {authenticateUser} from '../actions/authActions';

import UsernamePasswordLogin from '../components/login/UsernamePasswordLogin';

require('bootstrap/dist/css/bootstrap.css');
require('../styles/login.css');

class Login extends Component {
  componentDidMount() {
    const progressBar = document.getElementById('ipl-progress-indicator')
    if (progressBar) {
      setTimeout(() => {
        progressBar.classList.add('available')
        setTimeout(() => {
          progressBar.outerHTML = ''
        }, 2000)
      }, 1000)
    }
  }

  render() {
    var {redirect} = queryString.parse(this.context.router.route.location.search);

    if (redirect == undefined) {
      redirect = require('../config/config.json').defaultRedirectUrl;
    }

    return (
      <div className='container'>
        <UsernamePasswordLogin authenticateUser={authenticateUser} redirectUrl={redirect}/>
      </div>
    );
  }
}

Login.contextTypes = {
  router: PropTypes.object.isRequired
}

export default Login;