'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {Route, Switch, Redirect} from 'react-router-dom';

import {bindActionCreators} from 'redux';
import  {connect} from 'react-redux';

import {login} from '../../actions/authActions'
import {addFlashMessage} from '../../actions/flashMessageActions';
import {requestPasswordReset, validateResetToken, resetPassword} from '../../actions/resetPasswordActions';
import {doesPasswordMatchFormat, doPasswordsMatch} from '../../actions/registrationActions';

import LoginForm from '../../components/login/index';
import RequestPasswordResetForm from '../../components/login/resetpassword/RequestPasswordResetForm';
import ResetPasswordForm from '../../components/login/resetpassword/ResetPasswordForm';

class Login extends React.Component {
  render() {
    const {login, requestPasswordReset, addFlashMessage, validateResetToken, resetPassword, doesPasswordMatchFormat, doPasswordsMatch} = this.props.actions;

    return (
      <Switch>
        <Route exact path='/login'>
          <LoginForm login={login} addFlashMessage={addFlashMessage}/>
        </Route>

        <Route path='/login/request-password-reset'>
          <RequestPasswordResetForm requestPasswordReset={requestPasswordReset} addFlashMessage={addFlashMessage}/>
        </Route>

        <Route path='/login/reset-password'>
          <ResetPasswordForm validateResetToken={validateResetToken} resetPassword={resetPassword}
                             doesPasswordMatchFormat={doesPasswordMatchFormat}
                             doPasswordsMatch={doPasswordsMatch} addFlashMessage={addFlashMessage}/>
        </Route>
      </Switch>
    );
  }
}

Login.propTypes = {
  actions: PropTypes.object.isRequired
}

Login.contextTypes = {
  router: PropTypes.object.isRequired
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      login: bindActionCreators(login, dispatch),
      requestPasswordReset: bindActionCreators(requestPasswordReset, dispatch),
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch),
      validateResetToken: bindActionCreators(validateResetToken, dispatch),
      resetPassword: bindActionCreators(resetPassword, dispatch),
      doesPasswordMatchFormat: bindActionCreators(doesPasswordMatchFormat, dispatch),
      doPasswordsMatch: bindActionCreators(doPasswordsMatch, dispatch)
    }
  }
}

export default connect(null, mapDispatchToProps)(Login);