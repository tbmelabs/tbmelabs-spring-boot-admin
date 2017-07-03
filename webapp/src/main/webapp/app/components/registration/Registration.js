'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
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
    const {registerUser, addFlashMessage, isUsernameUnique, isEmailUnique, doesPasswordMatchFormat, doPasswordsMatch} = this.props.actions;

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
  actions: PropTypes.object.isRequired
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      registerUser: bindActionCreators(registerUser, dispatch),
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch),
      isUsernameUnique: bindActionCreators(isUsernameUnique, dispatch),
      isEmailUnique: bindActionCreators(isEmailUnique, dispatch),
      doesPasswordMatchFormat: bindActionCreators(doesPasswordMatchFormat, dispatch),
      doPasswordsMatch: bindActionCreators(doPasswordsMatch, dispatch)
    }
  }
}

export default connect(null, mapDispatchToProps)(Registration);