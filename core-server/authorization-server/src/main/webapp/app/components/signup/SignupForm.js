'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../common/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

class SignupForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      email: '',
      password: '',
      confirmation: '',
      errors: {},
      isValid: false,
      isLoading: false
    }

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value});
  }

  onSubmit(event) {
    event.preventDefault();
  }

  render() {
    const {isValid, isLoading} = this.state;
    const {texts} = this.props;

    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert style='danger' title={texts.signup_failed_error_title} message={this.state.errors.form}
                          collapse={!!this.state.errors.form}/>

        <FormGroup controlId='username'>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.username_form_control}
          </Col>
          <Col sm={8}>
            <FormControl name='username' type='text' value={this.state.username}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='email'>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.email_form_control}
          </Col>
          <Col sm={8}>
            <FormControl name='email' type='email' value={this.state.email}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='password'>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.password_form_control}
          </Col>
          <Col sm={8}>
            <FormControl name='password' type='password' value={this.state.password}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='confirmation'>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.password_confirmation_form_control}
          </Col>
          <Col sm={8}>
            <FormControl name='confirmation' type='password' value={this.state.confirmation}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup className='link-group'>
          <Col sm={4}>
          </Col>
          <Col sm={4}>
            <Link to='/' className='pull-left'>{texts.signin_link_text}</Link>
          </Col>
          <Col sm={4}>
            <Button className='pull-right' type='submit' disabled={!isValid || isLoading}
                    onClick={isValid && !isLoading ? this.handleClick : null}>
              {isLoading ? texts.signup_button_loading_text : texts.signup_button_text}
            </Button>
          </Col>
        </FormGroup>
      </Form>
    );
  }
}

SignupForm.propTypes = {
  texts: PropTypes.object.isRequired
}

export default SignupForm;