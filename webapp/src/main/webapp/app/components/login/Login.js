'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import  {connect} from 'react-redux';

import LoginForm from '../../containers/login/LoginForm';

import {login} from '../../actions/authActions'

class Login extends React.Component {
  render() {
    const {login} = this.props;

    return (
      <LoginForm login={login}/>
    );
  }
}

Login.propTypes = {
  login: PropTypes.func.isRequired
}

Login.contextTypes = {
  router: PropTypes.object.isRequired
}

export default connect(null, {login})(Login);