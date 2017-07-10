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

      if (this.props.accessLevel < requiredLevel) {
        addFlashMessage({
          type: 'danger',
          text: 'You have insufficent permissions to access this page!'
        });
        this.context.router.history.back();
      }
    }

    componentWillUpdate(nextProps) {
      if (!nextProps.accessLevel < requiredLevel) {
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
    accessLevel: PropTypes.int.isRequired,
    addFlashMessage: PropTypes.func.isRequired
  }

  Authenticate.contextTypes = {
    router: PropTypes.object.isRequired
  }

  function mapStateToProps(state) {
    return {
      isAuthenticated: state.auth.isAuthenticated,
      accessLevel: state.auth.user.accessLevel.id
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