'use strict';

import React from "react";

import PropTypes from 'prop-types';

import Alert from 'react-bootstrap/lib/Alert';
import Form from 'react-bootstrap/lib/Form';
import FromGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import validateInput from "../../utils/validators/registration";

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
      isLoading: false,
      isValid: false
    };

    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
    this.checkUserExists = this.checkUserExists.bind(this);
    this.checkEmailExists = this.checkEmailExists.bind(this);
    this.handleValidationResponse = this.handleValidationResponse.bind(this);
    this.checkPasswordFormat = this.checkPasswordFormat.bind(this);
    this.checkPasswordsMatch = this.checkPasswordsMatch.bind(this);
  }

  isValid() {
    const {errors, isValid} = validateInput(this.state);

    if (!isValid) {
      this.setState({errors});
    }

    return isValid;
  }

  onSubmit(event) {
    event.preventDefault();

    if (this.isValid()) {
      this.setState({errors: {}, isLoading: true});
      this.props.registerUser(this.state).then(
        () => {
          this.props.addFlashMessage({
            type: 'success',
            text: 'You signed up successfully. Welcome to TBME Labs TV!'
          });
          this.context.router.push('/');
        },
        (err) => this.setState({errors: err.response.data, isLoading: false})
      );
    }
  }

  onChange(event) {
    this.setState({[e.target.name]: e.target.value});
  }

  checkUserExists(event) {
    const field = event.target.name;
    const value = event.target.value;

    if (value !== '') {
      this.props.isUsernameUnique(value).then(res => {
        this.handleValidationResponse(field, res.data.username);
      });
    }
  }

  checkEmailExists(event) {
    const field = event.target.name;
    const value = event.target.value;

    if (value !== '') {
      this.props.isEmailUnique(value).then(res => {
        this.handleValidationResponse(field, res.data.email);
      });
    }
  }

  handleValidationResponse(field, data) {
    let errors = this.state.errors;
    let invalid;

    if (data) {
      errors[field] = 'User with this ' + field + ' already exists';
      invalid = true;
    } else {
      errors[field] = '';
      invalid = false;
    }

    this.setState({errors, invalid});
  }

  checkPasswordFormat(event) {
    const field = event.target.name;
    const value = event.target.value;

    if (value !== '') {
      this.props.doesPasswordMatchFormat(value).then(res => {
        let errors = this.state.errors;
        let invalid;

        if (data) {
          errors[field] = 'Password does not match format';
          invalid = true;
        } else {
          errors[field] = '';
          invalid = false;
        }

        this.setState({errors, invalid});
      });
    }
  }

  checkPasswordsMatch(event) {
    const field = event.target.name;
    const value = event.target.value;

    if (value !== '' && this.state.password !== '') {
      this.props.doPasswordsMatch(this.state.password, value).then(res => {
        let errors = this.state.errors;
        let invalid;

        if (data) {
          errors[field] = 'Passwords do not match';
          invalid = true;
        } else {
          errors[field] = '';
          invalid = false;
        }

        this.setState({errors, invalid});
      });
    }
  }

  render() {
    return (
      <Form onSubmit={this.handleSubmit}>
        {errors.form && <Alert bsStyle='danger'>{errors.form}</Alert>}

        <FormGroup controlId='username'>
          <Col componentClass='ControlLabel' sm={2}>
            Username
          </Col>
          <Col sm={10}>
            <FormControl type='text' value={this.state.username} onChange={this.onChange}
                         onBlur={this.checkUserExists}/>
          </Col>
          {this.state.errors.username && <HelpBlock>{this.state.errors.username}</HelpBlock>}
        </FormGroup>

        <FormGroup controlId='email'>
          <Col componentClass='ControlLabel' sm={2}>
            E-Mail
          </Col>
          <Col sm={10}>
            <FormControl type='text' value={this.state.email} onChange={this.onChange} onBlur={this.checkEmailExists}/>
          </Col>
          {this.state.errors.email && <HelpBlock>{this.state.errors.email}</HelpBlock>}
        </FormGroup>

        <FormGroup controlId='password'>
          <Col componentClass='ControlLabel' sm={2}>
            Password
          </Col>
          <Col sm={10}>
            <FormControl type='password' value={this.state.password} onChange={this.onChange}
                         onBlur={this.checkPasswordFormat}/>
          </Col>
          {this.state.errors.username && <HelpBlock>{this.state.errors.password}</HelpBlock>}
        </FormGroup>

        <FormGroup controlId='passwordMatch'>
          <Col componentClass='ControlLabel' sm={2}>
            Confirm password
          </Col>
          <Col sm={10}>
            <FormControl type='password' value={this.state.passwordMatch} onChange={this.onChange}
                         onBlur={this.checkPasswordsMatch}/>
          </Col>
          {this.state.errors.username && <HelpBlock>{this.state.errors.passwordMatch}</HelpBlock>}
        </FormGroup>

        <FormControl.Feedback />

        <Button type='submit'>Register</Button>
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