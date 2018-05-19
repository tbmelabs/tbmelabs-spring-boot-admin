// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {signoutUser} from '../../state/queries/authentication';

import Navigation from '../../components/Navigation';

class Navbar extends Component<Navbar.propTypes> {
  render() {
    const {profile, texts} = this.props;

    return (
        <div>
          <Navigation account={profile} logout={signoutUser} texts={texts}/>
        </div>
    );
  }
}

Navbar.propTypes = {
  profile: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    profile: state.profile,
    texts: state.language.texts.navbar
  };
}

export default connect(mapStateToProps)(Navbar);