'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {validateSignupForm, signupUser} from '../actions/signupActions';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';

import SignupForm from '../components/signup/SignupForm';

require('bootstrap/dist/css/bootstrap.css');
require('../styles/signup.css');

class Signup extends Component {
  componentDidMount() {
    document.title = this.props.texts.tab_header;
  }

  render() {
    const {texts} = this.props;

    return (
      <div className="container">
        <Jumbotron>
          <h1>{texts.jumbotron_title}</h1>
        </Jumbotron>

        <div className='signup-form'>
          <SignupForm validateForm={validateSignupForm} signupUser={signupUser} texts={texts}/>
        </div>
      </div>
    );
  }
}

Signup.propTypes = {
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.signup
  }
}

export default connect(mapStateToProps, null)(Signup);