'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import CollapsableAlert from '../../common/alert/CollapsableAlert';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import getQueryParams from '../../../utils/getQueryParams';

import validator from 'validator';
import validateInput from '../../../utils/validators/resetPasswordValidator';

require('bootstrap/dist/css/bootstrap.css');

class ResetPasswordForm extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      resetToken: '',
      password: '',
      passwordMatch: '',
      errors: {},
      isValid: false,
      isLoading: false
    };

    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.isPasswordResetTokenValid = this.isPasswordResetTokenValid.bind(this);
    this.checkPasswordFormat = this.checkPasswordsMatch.bind(this);
    this.checkPasswordsMatch = this.checkPasswordsMatch.bind(this);
  }

  componentDidMount() {
    this.isPasswordResetTokenValid(getQueryParams(this.context.router.route.location.search).resetToken);
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

      this.props.resetPassword(this.state).then(response => {
          this.props.addFlashMessage({
            type: 'success',
            text: 'Your password has been reseted.'
          });

          this.context.router.history.push('/login');
        }, error => this.setState({errors: {form: error.response.data.message}})
      );
    }
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value}, this.isValid);
  }

  isPasswordResetTokenValid(token) {
    if (token === undefined) {
      this.setState({errors: {form: 'Could not find password reset token!'}});
      return;
    }

    this.props.validateResetToken(token).then(
      response => this.setState({errors: {}, resetToken: token}),
      error => this.setState({errors: {form: error.response.data.message}})
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
  }

  render() {
    const isLoading = this.state.isLoading;
    const isValid = this.state.isValid;

    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert collapse={!!this.state.errors.form} style='danger' title='An error occurred: '
                          message={this.state.errors.form}/>

        <FormGroup controlId='password' validationState={!!this.state.errors.password ? 'error' : null}>
          <Col sm={2}>
            <ControlLabel>Password</ControlLabel>
            <HelpBlock>{this.state.errors.password}</HelpBlock>
          </Col>

          <Col sm={10}>
            <FormControl name='password' type='password' value={this.state.password} onChange={this.onChange}
                         onBlur={this.checkPasswordFormat} disabled={this.state.errors.form}/>
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
                         onBlur={this.checkPasswordsMatch} disabled={this.state.errors.form}/>
            <FormControl.Feedback />
          </Col>
        </FormGroup>

        <Button type='submit' disabled={isLoading || !isValid}
                onClick={!isLoading && isValid ? this.handleClick : null}>{isLoading ? 'Loading...' : 'Reset Password'}</Button>
      </Form>
    );
  }
}

ResetPasswordForm.propTypes = {
  validateResetToken: PropTypes.func.isRequired,
  resetPassword: PropTypes.func.isRequired,
  doesPasswordMatchFormat: PropTypes.func.isRequired,
  doPasswordsMatch: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired
}

ResetPasswordForm.contextTypes = {
  router: PropTypes.object.isRequired
}

export default ResetPasswordForm;