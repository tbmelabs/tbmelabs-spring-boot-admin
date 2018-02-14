'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {parse} from 'query-string';

import {addFlashMessage} from '../../../common/actions/flashMessageActions';

class ShouldAddGoodbyeMessage extends Component {
  componentDidMount() {
    const {texts} = this.props;
    const {addFlashMessage} = this.props.actions;

    if (parse(window.location.search.substr(1)).goodbye !== undefined) {
      addFlashMessage({
        type: 'success',
        title: texts.logout_succeed_alert_title,
        text: texts.logout_succeed_alert_text
      })
    }
  }

  render() {
    return (null);
  }
}

ShouldAddGoodbyeMessage.propTypes = {
  actions: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.signin
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(ShouldAddGoodbyeMessage);