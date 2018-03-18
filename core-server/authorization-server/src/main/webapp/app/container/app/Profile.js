// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import loadProfile from '../../utils/loadProfile';
import {setProfile} from '../../actions/profileActions';

import AccountInformation from '../../components/profile/AccountInformation';

require('../../styles/profile.css');

class Profile extends Component<Profile.propTypes> {
  componentWillMount() {
    const {texts} = this.props;

    loadProfile().then(
      response => this.props.actions.setProfile(response.data),
      error => this.setState({errors: {form: texts.errors.load}})
    );
  }

  render() {
    const {profile, texts} = this.props;

    return (
      <div>
        <AccountInformation account={profile} texts={texts}/>
      </div>
    );
  }
}

Profile.propTypes = {
  profile: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired,
  actions: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    profile: state.profile,
    texts: state.language.texts.profile
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      setProfile: bindActionCreators(setProfile, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile);