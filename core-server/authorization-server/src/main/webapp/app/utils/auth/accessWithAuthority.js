// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import isEmpty from 'lodash/isEmpty';

import hasAuthority from './hasAuthority';
import {addFlashMessage} from '../../state/queries/flashmessage';

// TODO: ComposedComponent cannot be "Component" because of missing 1-2 type arguments
export default (ComposedComponent: any, authority: string) => {
  class AccessWithAuthority extends Component<AccessWithAuthority.propTypes, AccessWithAuthority.contextTypes> {
    displayWarningMessage: (props: AccessWithAuthority.propTypes) => void;

    constructor(props, context) {
      super(props, context);

      this.displayWarningMessage = this.displayWarningMessage.bind(this);
    }

    componentWillMount() {
      const {profile} = this.props;

      if (!isEmpty(profile) && !hasAuthority(authority, profile)) {
        this.displayWarningMessage(this.props);
        this.context.router.history.push('/');
      }
    }

    componentWillUpdate(nextProps: AccessWithAuthority.propTypes) {
      const {profile} = nextProps;

      if (!hasAuthority(authority, profile)) {
        this.displayWarningMessage(nextProps);
        this.context.router.history.push('/');
      }
    }

    displayWarningMessage(props: AccessWithAuthority.propTypes) {
      const {texts} = props;

      addFlashMessage({
        type: 'warning',
        title: texts.access_denied_alert_title,
        text: texts.access_denied_alert_text
      });

    }

    render() {
      const {profile} = this.props;

      if (isEmpty(profile)) {
        return null;
      }

      return (
          <ComposedComponent {...this.props} />
      );
    }
  }

  AccessWithAuthority.propTypes = {
    profile: PropTypes.object.isRequired,
    texts: PropTypes.object.isRequired
  };

  AccessWithAuthority.contextTypes = {
    router: PropTypes.object.isRequired
  };

  function mapStateToProps(state) {
    return {
      profile: state.profile,
      texts: state.language.texts.app
    };
  }

  return connect(mapStateToProps)(AccessWithAuthority);
}
