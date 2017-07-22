'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {addFlashMessage} from '../../actions/flashMessageActions';

export default function (ComposedComponent, requiredLevel) {
  class Authenticate extends React.Component {
    componentWillMount() {
      const {addFlashMessage} = this.props.actions;
      const {isAuthenticated, user} = this.props;

      if (!isAuthenticated || user.accessLevel.id < requiredLevel) {
        addFlashMessage({
          type: 'danger',
          text: 'You have insufficent permissions to access this page!'
        });

        this.context.router.history.push('/login');
      }
    }

    componentWillUpdate(nextProps) {
      const {isAuthenticated, user} = nextProps;

      if (!isAuthenticated || user.accessLevel.id < requiredLevel) {
        this.context.router.history.back();
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
    user: PropTypes.object.isRequired,
    actions: PropTypes.object.isRequired
  }

  Authenticate.contextTypes = {
    router: PropTypes.object.isRequired
  }

  function mapStateToProps(state) {
    return {
      isAuthenticated: state.auth.isAuthenticated,
      user: state.auth.user
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