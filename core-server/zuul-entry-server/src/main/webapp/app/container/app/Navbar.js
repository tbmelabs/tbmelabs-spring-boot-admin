// @flow
'use strict';

import React, {Component} from 'react';

import {
  getIsAuthenticated,
  signinUser,
  signoutUser
} from '../../state/queries/authentication';
import {getTexts} from '../../state/queries/language';

import Navigation from '../../components/Navigation';

class Navbar extends Component<Navbar.propTypes> {
  render() {
    return (
        <div>
          <Navigation login={signinUser} logout={signoutUser}
                      isAuthenticated={getIsAuthenticated()}
                      texts={getTexts('navbar')}/>
        </div>
    );
  }
}

export default Navbar;
