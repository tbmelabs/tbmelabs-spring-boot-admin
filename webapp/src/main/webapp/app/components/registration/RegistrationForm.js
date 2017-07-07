'use strict';

import React from "react";
import PropTypes from 'prop-types';

import CollapsableAlert from '../common/alert/CollapsableAlert';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import validator from 'validator';
import validateInput from "../../utils/validators/registrationValidator";

require('bootstrap/dist/css/bootstrap.css');

class RegistrationForm extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      email: '',
      password: '',
      passwordMatch: '',
      errors: {},
      isValid: false,
      isLoading: false
    };

    this.isValid = this.isValid.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.checkUserExists = this.checkUserExists.bind(this);
    this.checkEmailExists = this.checkEmailExists.bind(this);
    this.checkPasswordFormat = this.checkPasswordFormat.bind(this);
    this.checkPasswordsMatch = this.checkPasswordsMatch.bind(this);
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

      this.props.registerUser(this.state).then(
        response => {
          this.props.addFlashMessage({
            type: 'success',
            text: 'You signed up successfully. Welcome to TBME Labs TV!'
          });

          this.context.router.history.push('/');
        }, error => this.setState({errors: {form: error.response.data.message}, isLoading: false})
      );
    }
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value});
  }

  checkUserExists(event) {
    let errors = this.state.errors;

    const field = event.target.name;
    const value = event.target.value;

    if (!validator.isEmpty(value)) {
      this.props.isUsernameUnique(value).then(
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

  checkEmailExists(event) {
    let errors = this.state.errors;

    const field = event.target.name;
    const value = event.target.value;

    if (!validator.isEmpty(value) && !validator.isEmail(value)) {
      errors[field] = 'Please enter a valid email address';
      this.setState({errors});
      return;
    } else if (!validator.isEmpty(value)) {
      this.props.isEmailUnique(value).then(
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
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert collapse={!!this.state.errors.form} style='danger' title='Registration failed: '
                          message={this.state.errors.form}/>

        <FormGroup controlId='username' validationState={!!this.state.errors.username ? 'error' : null}>
          <Col sm={2}>
            <ControlLabel>Username</ControlLabel>
            <HelpBlock>{this.state.errors.username}</HelpBlock>
          </Col>

          <Col sm={10}>
            <FormControl name='username' type='text' value={this.state.username} onChange={this.onChange}
                         onBlur={this.checkUserExists}/>
            <FormControl.Feedback />
          </Col>
        </FormGroup>

        <FormGroup controlId='email' validationState={!!this.state.errors.email ? 'error' : null}>
          <Col sm={2}>
            <ControlLabel>E-Mail</ControlLabel>
            <HelpBlock>{this.state.errors.email}</HelpBlock>
          </Col>

          <Col sm={10}>
            <FormControl name='email' type='text' value={this.state.email} onChange={this.onChange}
                         onBlur={this.checkEmailExists}/>
            <FormControl.Feedback />
          </Col>
        </FormGroup>

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

        <Button type='submit' disabled={isLoading && !isValid}
                onClick={!isLoading && isValid ? this.handleClick : null}>{isLoading ? 'Loading...' : 'Sign Up'}</Button>
      </Form>
    );
  }
}

RegistrationForm.propTypes = {
  registerUser: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  isUsernameUnique: PropTypes.func.isRequired,
  isEmailUnique: PropTypes.func.isRequired,
  doesPasswordMatchFormat: PropTypes.func.isRequired,
  doPasswordsMatch: PropTypes.func.isRequired
}

RegistrationForm.contextTypes = {
  router: PropTypes.object.isRequired
}

export default RegistrationForm;