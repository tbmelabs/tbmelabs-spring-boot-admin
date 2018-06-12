// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

class SigninButton extends Component<SigninButton.propTypes> {
  render() {
    const {signinUser, texts} = this.props;

    return (
        <button onClick={signinUser} className='btn btn-transparent'>
          <span>{texts.signin}</span>
        </button>
    );
  }
}

SigninButton.propTypes = {
  signinUser: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default SigninButton;
