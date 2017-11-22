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

    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert style='danger' title='Login failed: ' message={this.state.errors.form}
                          collapse={!!this.state.errors.form}/>

        <FormGroup controlId='username'>
          <Col componentClass={ControlLabel} sm={4}>
            Username
          </Col>
          <Col sm={8}>
            <FormControl name='username' type='text' value={this.state.username} onChange={this.onChange}/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='email'>
          <Col componentClass={ControlLabel} sm={4}>
            E-Mail
          </Col>
          <Col sm={8}>
            <FormControl name='email' type='email' value={this.state.email} onChange={this.onChange}/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='password'>
          <Col componentClass={ControlLabel} sm={4}>
            Password
          </Col>
          <Col sm={8}>
            <FormControl name='password' type='password' value={this.state.password}
                         onChange={this.onChange}/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='confirmation'>
          <Col componentClass={ControlLabel} sm={4}>
            Confirm password
          </Col>
          <Col sm={8}>
            <FormControl name='confirmation' type='password' value={this.state.confirmation}
                         onChange={this.onChange}/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup className='link-group'>
          <Col sm={4}>
          </Col>
          <Col sm={4}>
            <Link to='/' className='pull-left'>Already have an account?</Link>
          </Col>
          <Col sm={4}>
            <Button className='pull-right' type='submit' disabled={!isValid || isLoading}
                    onClick={isValid && !isLoading ? this.handleClick : null}>{isLoading ? 'Loading...' : 'Sign Up'}</Button>
          </Col>
        </FormGroup>
      </Form>
    );
  }
}

export default SignupForm;