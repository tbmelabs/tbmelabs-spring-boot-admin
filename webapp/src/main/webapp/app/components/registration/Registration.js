'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import RegistrationForm from '../../containers/registration/RegistrationForm';

import {addFlashMessage} from '../../actions/flashMessageActions';
import {
  registerUser,
  isUsernameUnique,
  isEmailUnique,
  doesPasswordMatchFormat,
  doPasswordsMatch
} from '../../actions/registrationActions';

class Registration extends React.Component {
  render() {
    const {registerUser, addFlashMessage, isUsernameUnique, isEmailUnique, doesPasswordMatchFormat, doPasswordsMatch} = this.props;
    return (
      <RegistrationForm
        registerUser={registerUser}
        addFlashMessage={addFlashMessage}
        isUsernameUnique={isUsernameUnique}
        isEmailUnique={isEmailUnique}
        doesPasswordMatchFormat={doesPasswordMatchFormat}
        doPasswordsMatch={doPasswordsMatch}
      />
    );
  }
}

Registration.propTypes = {
  registerUser: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  isUsernameUnique: PropTypes.func.isRequired,
  isEmailUnique: PropTypes.func.isRequired,
  doesPasswordMatchFormat: PropTypes.func.isRequired,
  doPasswordsMatch: PropTypes.func.isRequired
}


export default connect(null, {
  registerUser,
  addFlashMessage,
  isUsernameUnique,
  isEmailUnique,
  doesPasswordMatchFormat,
  doPasswordsMatch
})(Registration);