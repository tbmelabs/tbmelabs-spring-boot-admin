'use strict';

import React from 'react';

import {Link} from 'react-router-dom';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';
import PageHeader from 'react-bootstrap/lib/PageHeader';

require('bootstrap/dist/css/bootstrap.css');

class LandingPage extends React.Component {
  render() {
    return (
      <Jumbotron>
        <PageHeader>
          Welcome to TBME Labs TV
          <br/>
          <small>The open sourced and free to use streaming application.</small>
        </PageHeader>

        <p>You do not have an account yet? <Link to='/register'>Get started now for free!</Link></p>
      </Jumbotron>
    );
  }
}

export default LandingPage;