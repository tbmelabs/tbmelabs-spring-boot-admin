// @flow
'use strict';

import React, {Component} from 'react';

import Navigation from '../../components/Navigation';

class Navbar extends Component<Navbar.propTypes> {
  render() {
    const {auth, texts} = this.props;

    return (
      <div>
        <Navigation login={() => {}} logout={() => {}} isAuthenticated={false} texts={{}}/>
      </div>
    );
  }
}

export default Navbar;