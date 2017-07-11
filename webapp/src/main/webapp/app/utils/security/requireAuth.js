'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {addFlashMessage} from '../../actions/flashMessageActions';

export default function (ComposedComponent) {
  class Authenticate extends React.Component {
    componentWillMount() {
      const {addFlashMessage} = this.props.actions;

      if (!this.props.isAuthenticated) {
        addFlashMessage({
          type: 'warning',
          text: 'You need to login to access this page.'
        });
        this.context.router.history.push('/login');
      }
    }

    componentWillUpdate(nextProps) {
      if (!nextProps.isAuthenticated) {
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
    isAuthenticated: PropTypes.bool.isRequired,
    actions: PropTypes.object.isRequired
  }

  Authenticate.contextTypes = {
    router: PropTypes.object.isRequired
  }

  function mapStateToProps(state) {
    return {
      isAuthenticated: state.auth.isAuthenticated
    };
  }

  function mapDispatchToProps(dispatch) {
    return {
      actions: {
        addFlashMessage: bindActionCreators(addFlashMessage, dispatch)
      }
    }
  }

  return connect(mapStateToProps, mapDispatchToProps)(Authenticate);
}