// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../state/selectors/language';
import {getIsAuthenticated} from '../../state/selectors/authentication';
import {signinUser, signoutUser} from '../../state/queries/authentication';

import Navigation from '../../components/Navigation';

class Navbar extends Component<Navbar.propTypes> {
  render() {
    const {isAuthenticated, texts} = this.props;

    return (
        <div>
          <Navigation login={signinUser} logout={signoutUser}
                      isAuthenticated={isAuthenticated}
                      texts={texts}/>
        </div>
    );
  }
}

Navbar.propTypes = {
  isAuthenticated: PropTypes.bool.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    isAuthenticated: getIsAuthenticated(state),
    texts: getTexts(state).navbar
  }
}

export default connect(mapStateToProps)(Navbar);
