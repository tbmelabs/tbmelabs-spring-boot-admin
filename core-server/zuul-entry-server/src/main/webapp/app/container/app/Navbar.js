// @flow
'use strict';

import React, {Component} from 'react';

import {getIsAuthenticated, signinUser, signoutUser} from '../../queries/authentication';
import {getTexts} from '../../queries/language';

import Navigation from '../../components/Navigation';

class Navbar extends Component<Navbar.propTypes> {
  render() {
    const {auth, texts} = this.props;

    return (
      <div>
        <Navigation login={signinUser} logout={signoutUser} isAuthenticated={getIsAuthenticated()}
                    texts={getTexts('navbar')}/>
      </div>
    );
  }
}

export default Navbar;