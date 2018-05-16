// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {type userType} from '../../../common/types/user.type';

import isEmpty from 'lodash/isEmpty';
import debounce from 'lodash/debounce';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import {DEBOUNCE_DELAY} from '../../config';

require('bootstrap/dist/css/bootstrap.css');

type SignupFormState = {
  ...userType;
  target: HTMLInputElement;
  errors: { ...userType };
  hasChanged: boolean;
  isValid: boolean;
}

class SignupForm extends Component<SignupForm.propTypes, SignupFormState> {
  onChange: () => void;
  isFormValid: (errors: userType) => boolean;
  onSubmit: () => void;
  validateForm: (name: string, state: SignupFormState,
      callback: (errors: userType) => void) => void;

  constructor(props: SignupForm.propTypes) {
    super(props);

    this.state = {
      username: '',
      email: '',
      password: '',
      confirmation: '',
      target: HTMLInputElement.prototype,
      errors: {
        username: '',
        email: '',
        password: '',
        confirmation: '',
      },
      hasChanged: false,
      isValid: false
    };

    this.onChange = this.onChange.bind(this);
    this.isFormValid = this.isFormValid.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.validateForm = debounce(this.props.validateForm, DEBOUNCE_DELAY);
  }

  onChange(event: SyntheticInputEvent<HTMLInputElement>) {
    this.setState({
      hasChanged: true,
      [event.target.name]: event.target.value,
      target: event.target
    }, () => {
      this.validateForm(this.state.target.name, this.state, errors => {
        this.setState({errors: errors, isValid: this.isFormValid(errors)});
      });
    });
  }

  isFormValid(errors: userType) {
    const {username, email, password, confirmation} = this.state;

    return isEmpty(errors) && !!username && !!email && !!password
        && !!confirmation;
  }

  onSubmit(event: SyntheticInputEvent<HTMLInputElement>) {
    event.preventDefault();

    this.setState({hasChanged: false});

    this.props.validateForm(event.target.name, this.state, errors => {
      if (this.isFormValid(errors)) {
        this.props.signupUser(this.state);
      } else {
        this.setState({errors: errors, isValid: false});
      }
    });
  }

  render() {
    const {isValid, hasChanged, errors} = this.state;
    const {texts} = this.props;

    return (
        <Form onSubmit={this.onSubmit} horizontal>
          <FormGroup controlId='username'
                     validationState={!!errors.username ? 'error' : null}>
            <HelpBlock
                className='col-sm-6 col-sm-offset-4'>{errors.username}</HelpBlock>
            <Col componentClass={ControlLabel} sm={4}>
              {texts.username_form_control}
            </Col>
            <Col sm={6}>
              <FormControl name='username' type='text'
                           value={this.state.username}
                           onChange={this.onChange} required/>
              <FormControl.Feedback/>
            </Col>
          </FormGroup>

          <FormGroup controlId='email'
                     validationState={!!errors.email ? 'error' : null}>
            <HelpBlock
                className='col-sm-6 col-sm-offset-4'>{errors.email}</HelpBlock>
            <Col componentClass={ControlLabel} sm={4}>
              {texts.email_form_control}
            </Col>
            <Col sm={6}>
              <FormControl name='email' type='email' value={this.state.email}
                           onChange={this.onChange} required/>
              <FormControl.Feedback/>
            </Col>
          </FormGroup>

          <FormGroup controlId='password'
                     validationState={!!errors.password ? 'error' : null}>
            <HelpBlock
                className='col-sm-6 col-sm-offset-4'>{errors.password}</HelpBlock>
            <Col componentClass={ControlLabel} sm={4}>
              {texts.password_form_control}
            </Col>
            <Col sm={6}>
              <FormControl name='password' type='password'
                           value={this.state.password}
                           onChange={this.onChange} required/>
              <FormControl.Feedback/>
            </Col>
          </FormGroup>

          <FormGroup controlId='confirmation'
                     validationState={!!errors.confirmation ? 'error' : null}>
            <HelpBlock
                className='col-sm-6 col-sm-offset-4'>{this.state.errors.confirmation}</HelpBlock>
            <Col componentClass={ControlLabel} sm={4}>
              {texts.password_confirmation_form_control}
            </Col>
            <Col sm={6}>
              <FormControl name='confirmation' type='password'
                           value={this.state.confirmation}
                           onChange={this.onChange} required/>
              <FormControl.Feedback/>
            </Col>
          </FormGroup>

          <FormGroup className='link-group'>
            <Col smOffset={4} sm={3}>
              <a href='signin'
                 className='pull-left'>{texts.signin_link_text}</a>
            </Col>
            <Col sm={3}>
              <Button type='submit' bsStyle='primary' className='pull-right'
                      disabled={!isValid && !hasChanged}
                      onClick={isValid && hasChanged ? this.onSubmit : null}>
                {texts.signup_button_text}
              </Button>
            </Col>
          </FormGroup>
        </Form>
    );
  }
}

SignupForm.propTypes = {
  validateForm: PropTypes.func.isRequired,
  signupUser: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
};

export default SignupForm;
