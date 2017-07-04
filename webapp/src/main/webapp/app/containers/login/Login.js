'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import  {connect} from 'react-redux';

import {login} from '../../actions/authActions'

import LoginForm from '../../components/login/LoginForm';

class Login extends React.Component {
  render() {
    const {login} = this.props.actions;

    return (
      <LoginForm login={login}/>
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
      login: bindActionCreators(login, dispatch)
    }
  }
}

export default connect(null, mapDispatchToProps)(Login);