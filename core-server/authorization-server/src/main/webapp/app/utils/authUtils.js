'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import axios from 'axios';

export function setAuthorizationToken(token) {
  if (token) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete axios.defaults.headers.common['Authorization'];
  }
}

export function anonymousUsersOnly(ComposedComponent) {
  class Anonymous extends React.Component {
    constructor(props) {
      super(props);

      this.anonymousUserCheck = this.anonymousUserCheck.bind(this);
    }

    componentWillMount() {
      this.anonymousUserCheck();
    }

    componentWillUpdate(nextProps) {
      this.anonymousUserCheck();
    }

    anonymousUserCheck() {
      const {isAuthenticated} = this.props;

      if (isAuthenticated) {
        this.context.router.history.push('/select');
      }
    }

    render() {
      return (
        <ComposedComponent {...this.props} />
      );
    }
  }

  Anonymous.propTypes = {
    isAuthenticated: PropTypes.bool.isRequired
  }

  Anonymous.contextTypes = {
    router: PropTypes.object.isRequired
  }

  function mapStateToProps(state) {
    return {
      isAuthenticated: state.auth.isAuthenticated
    };
  }

  return connect(mapStateToProps, null)(Anonymous);
}

export function authenticatedUsersOnly(ComposedComponent) {
  class Authenticate extends React.Component {
    constructor(props) {
      super(props);

      this.authenticatedUserCheck = this.authenticatedUserCheck.bind(this);
    }

    componentWillMount() {
      this.authenticatedUserCheck();
    }

    componentWillUpdate(nextProps) {
      this.authenticatedUserCheck();
    }

    authenticatedUserCheck() {
      const {isAuthenticated} = this.props;

      if (!isAuthenticated) {
        this.context.router.history.push('/');
      }
    }

    render() {
      return (
        <ComposedComponent {...this.props} />
      );
    }
  }

  Authenticate.propTypes = {
    isAuthenticated: PropTypes.bool.isRequired
  }

  Authenticate.contextTypes = {
    router: PropTypes.object.isRequired
  }

  function mapStateToProps(state) {
    return {
      isAuthenticated: state.auth.isAuthenticated
    };
  }

  return connect(mapStateToProps, null)(Authenticate);
}