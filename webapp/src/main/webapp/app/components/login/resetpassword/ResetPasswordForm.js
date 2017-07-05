'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import CollapsableAlert from '../../common/alert/CollapsableAlert';

import getQueryParams from '../../../utils/getQueryParams';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import Button from 'react-bootstrap/lib/Button';

import validator from 'validator';
import validateInput from '../../../utils/validators/resetPasswordValidator';

require('bootstrap/dist/css/bootstrap.css');

class ResetPasswordForm extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      resetToken: getQueryParams(this.props.location.search).resetToken,
      password: '',
      passwordMatch: '',
      errors: {},
      isValid: false,
      isLoading: false
    };

    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  componentDidMount() {
    this.isPasswordResetTokenValid();
  }

  isValid(validate) {
    const {errors, isValid} = validateInput(this.state);

    if (!isValid && validate) {
      this.setState({errors, isValid});
    } else {
      this.setState({isValid});
    }

    return isValid;
  }

  onSubmit(event) {
    event.preventDefault();

    if (this.isValid(true)) {
      this.setState({errors: {}, isLoading: true});

      this.props.resetPassword(this.state).then(
        response => this.context.router.history.push('/login'),
        error => this.setState({errors: {form: error.response.data.message}})
      );
    }
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value});
  }

  isPasswordResetTokenValid() {
    const token = this.state.resetToken;

    if (token === undefined) {
      this.setState({passwordResetError: 'Could not find password reset token!'});
      return;
    }

    this.props.validateResetToken(token).then(
      response => self.setState({errors: {token: {}}}),
      error => self.setState({errors: {token: error.response.data.message}})
    );
  }

  checkPasswordFormat(event) {
    let errors = this.state.errors;

    const field = event.target.name;
    const value = event.target.value;

    if (!validator.isEmpty(value)) {
      this.props.doesPasswordMatchFormat(value).then(
        response => {
          errors[field] = '';
          this.setState({errors});
        }, error => {
          errors[field] = error.response.data.message;
          this.setState({errors});
        }
      );
    }

    this.isValid(false);
  }

  checkPasswordsMatch(event) {
    let errors = this.state.errors;

    const field = event.target.name;
    const value = event.target.value;

    if (!validator.isEmpty(this.state.password) && !validator.isEmpty(this.state.passwordMatch)) {
      this.props.doPasswordsMatch(this.state.password, this.state.passwordMatch).then(
        response => {
          errors[field] = '';
          this.setState({errors});
        }, error => {
          errors[field] = error.response.data.message;
          this.setState({errors});
        }
      );
    }

    this.isValid(false);
  }

  render() {
    let isValid = this.state.isValid;
    let isLoading = this.state.isLoading;

    return (
      <Form onSubmit={this.handleSubmit} horizontal>
        <CollapsableAlert collapse={!!this.state.errors.form} style='danger' title='An error occurred: '
                          message={this.state.errors.form}/>

        <FormGroup controlId='password' validationState={!!this.state.errors.password ? 'error' : null}>
          <Col sm={2}>
            <ControlLabel>Password</ControlLabel>
            <HelpBlock>{this.state.errors.password}</HelpBlock>
          </Col>

          <Col sm={10}>
            <FormControl name='password' type='password' value={this.state.password} onChange={this.onChange}
                         onBlur={this.checkPasswordFormat}/>
            <FormControl.Feedback />
          </Col>
        </FormGroup>

        <FormGroup controlId='passwordMatch' validationState={!!this.state.errors.passwordMatch ? 'error' : null}>
          <Col sm={2}>
            <ControlLabel>Confirm Password</ControlLabel>
            <HelpBlock>{this.state.errors.passwordMatch}</HelpBlock>
          </Col>

          <Col sm={10}>
            <FormControl name='passwordMatch' type='password' value={this.state.passwordMatch} onChange={this.onChange}
                         onBlur={this.checkPasswordsMatch}/>
            <FormControl.Feedback />
          </Col>
        </FormGroup>

        <Button type='submit' active={!isLoading && isValid} disabled={isLoading && !isValid}
                onClick={!isLoading && isValid ? this.handleClick : null}>{isLoading ? 'Loading...' : 'Reset Password'}</Button>
      </Form>
    );
  }
}

ResetPasswordForm.propTypes = {
  resetPassword: PropTypes.func.isRequired,
  doesPasswordMatchFormat: PropTypes.func.isRequired,
  doPasswordsMatch: PropTypes.func.isRequired
}

ResetPasswordForm.contextTypes = {
  router: PropTypes.object.isRequired
}

export default ResetPasswordForm;