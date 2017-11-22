'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import SignupForm from '../components/signup/SignupForm';

require('bootstrap/dist/css/bootstrap.css');
require('../styles/signup.css');

class Signup extends Component {
  componentDidMount() {
    document.title = 'TBME Labs | Sign Up';
  }

  render() {
    return (
      <div className="container">
        <div className='signup-form'>
          <SignupForm/>
        </div>
      </div>
    );
  }
}

export default connect(null, null)(Signup);