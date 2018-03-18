// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import loadProfile from '../../utils/loadProfile';
import {setProfile} from '../../actions/profileActions';

require('../../styles/profile.css');

class Profile extends Component<Profile.propTypes> {
  componentWillMount() {
    const {texts} = this.props;

    loadProfile().then(
      response => this.props.actions.setProfile(response.data),
      error => this.setState({errors: {form: texts /*TODO: Set error */}})
    );
  }

  render() {
    const {texts} = this.props;

    return (
      <h1>Hello World</h1>
    );
  }
}

Profile.propTypes = {
  texts: PropTypes.object.isRequired,
  actions: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.authorize
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      setProfile: bindActionCreators(setProfile, dispatch)
    }
  }
}

export default connect(mapStateToProps)(Profile);