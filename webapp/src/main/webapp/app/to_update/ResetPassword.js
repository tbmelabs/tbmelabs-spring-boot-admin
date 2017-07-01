'use strict';

import React from 'react';

import axios from 'axios';

import getQueryParams from '../utils/getQueryParams';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import Button from 'react-bootstrap/lib/Button';

import CustomAlert from './CustomAlert.js';
import InputErrorOverlay from './InputErrorOverlay.js';

require('bootstrap/dist/css/bootstrap.css');

class ResetPassword extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      resetToken: getQueryParams(this.props.location.search).resetToken,
      password: '',
      passwordError: '',
      passwordMatch: '',
      passwordMatchError: '',
      passwordResetError: ''
    };
    console.log(this.state);

    this.isPasswordResetTokenValid = this.isPasswordResetTokenValid.bind(this);

    this.passwordChanged = this.passwordChanged.bind(this);
    this.validatePassword = this.validatePassword.bind(this);
    this.passwordValidationState = this.passwordValidationState.bind(this);

    this.passwordMatchChanged = this.passwordMatchChanged.bind(this);
    this.validatePasswordMatch = this.validatePasswordMatch.bind(this);
    this.passwordMatchValidationState = this.passwordMatchValidationState.bind(this);

    this.submitButtonDisabled = this.submitButtonDisabled.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.isPasswordResetTokenValid();
  }

  isPasswordResetTokenValid() {
    if (this.state.resetToken === undefined) {
      this.setState({passwordResetError: 'Could not find password reset token!'});
      return;
    }

    var self = this;

    axios.get('/login/reset-password/' + this.state.resetToken)
      .catch(error => {
        self.setState({passwordResetError: error.response.data.message});
      });
  }

  passwordChanged(event) {
    console.log(this.state);
    this.setState({password: event.target.value});
  }

  validatePassword() {
    if (this.state.passwordMatch !== '') {
      this.validatePasswordMatch(event);
    }

    var self = this;

    axios.post('/register/check/does-password-match-format', {password: this.state.password})
      .then(response => {
        self.setState({passwordError: ''});
      }).catch(error => {
      self.setState({passwordError: error.response.data.message});
    });
  }

  passwordValidationState() {
    return this.state.password !== '' && this.state.passwordError !== '' ? 'error' : null;
  }

  passwordMatchChanged(event) {
    this.setState({passwordMatch: event.target.value});
  }

  validatePasswordMatch() {
    var self = this;

    axios.post('/register/check/do-passwords-match', {
      password: this.state.password,
      passwordMatch: this.state.passwordMatch
    }).then(response => {
      self.setState({passwordMatchError: ''});
    }).catch(error => {
      self.setState({passwordMatchError: error.response.data.message});
    });
  }

  passwordMatchValidationState() {
    return this.state.passwordMatch !== '' && this.state.passwordMatchError !== '' ? 'error' : null;
  }

  submitButtonDisabled() {
    return this.state.passwordResetError !== '' || this.state.password === '' || this.passwordValidationState() !== null
      || this.state.passwordMatch === '' || this.passwordMatchValidationState() !== null;
  }

  handleSubmit(event) {
    event.preventDefault();

    var self = this;

    axios.post('/login/reset-password/' + this.state.resetToken, {
      password: this.state.password,
      passwordMatch: this.state.passwordMatch
    }).then(response => {
      self.props.history.push('/login');
    }).catch(error => {
      self.setState({registrationError: error.response.data.message})
    });
  }

  render() {
    return (
      <Form onSubmit={this.handleSubmit}>
        {this.state.passwordResetError !== '' ? <CustomAlert type='danger' title='Reset failed'
                                                             message={this.state.passwordResetError}/> :
          <CustomAlert className='invisible'/>
        }

        < InputErrorOverlay show={this.passwordValidationState()}
                            target={document.getElementById('formAccountPassword')}
                            container={this} title='Error' message={this.state.passwordError}/>
        <FormGroup controlId='formAccountPassword' validationState={this.passwordValidationState()}>
          <ControlLabel> Password: <FormControl type='password'
                                                disabled={this.state.passwordResetError !== ''}
                                                value={this.state.password} onChange={this.passwordChanged}
                                                onBlur={this.validatePassword}/>
          </ControlLabel>
        </FormGroup>

        <InputErrorOverlay show={this.passwordMatchValidationState()}
                           target={document.getElementById('formAccountPasswordMatch')}
                           container={this} title='Error' message={this.state.passwordMatchError}/>
        <FormGroup controlId='formAccountPasswordMatch' validationState={this.passwordMatchValidationState()}>
          <ControlLabel> Confirm password: <FormControl type='password'
                                                        disabled={this.state.passwordResetError !== ''}
                                                        value={this.state.passwordMatch}
                                                        onChange={this.passwordMatchChanged}
                                                        onBlur={this.validatePasswordMatch}/>
          </ControlLabel>
        </FormGroup>

        <FormControl.Feedback />

        <Button type='submit' disabled={this.submitButtonDisabled()}>Update Password</Button>
      </Form>
    );
  }
}

export default ResetPassword;