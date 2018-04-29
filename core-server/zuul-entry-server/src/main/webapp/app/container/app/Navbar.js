// @flow
'use strict';

import React, {Component} from 'react';

import AuthenticationQueries from '../../queries/authentication.queries';
import LanguageQueries from '../../queries/language.queries';

import Navigation from '../../components/Navigation';

class Navbar extends Component<Navbar.propTypes> {
  render() {
    const {auth, texts} = this.props;

    return (
      <div>
        <Navigation login={AuthenticationQueries.signin} logout={AuthenticationQueries.signout}
                    isAuthenticated={AuthenticationQueries.isAuthenticated} texts={LanguageQueries.getTexts('navbar')}/>
      </div>
    );
  }
}

export default Navbar;