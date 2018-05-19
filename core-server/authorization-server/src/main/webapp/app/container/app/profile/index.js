// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../../state/selectors/language';
import {getProfile} from '../../../state/selectors/profile';
import {requestProfile} from '../../../state/queries/profile';

import AccountInformation from '../../../components/profile/AccountInformation';

require('../../../styles/profile.css');

class Profile extends Component<Profile.propTypes> {
  componentWillMount() {
    requestProfile();
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
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    profile: getProfile(state),
    texts: getTexts(state).profile
  };
}

export default connect(mapStateToProps)(Profile);