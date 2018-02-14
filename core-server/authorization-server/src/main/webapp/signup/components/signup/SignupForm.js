'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import isEmpty from 'lodash/isEmpty';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

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
    this.validateForm = this.validateForm.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value, target: event.target}, () => {
      this.props.validateForm(this.state.target.name, this.state, errors => {
        this.setState({errors: errors, isValid: this.validateForm(errors)});
      });
    });
  }

  validateForm(errors) {
    return isEmpty(errors) && !!this.state.username && !!this.state.email
      && !!this.state.password && !!this.state.confirmation;
  }

  onSubmit(event) {
    event.preventDefault();

    const {texts} = this.props;

    this.props.validateForm(event.target.name, this.state, errors => {
      if (this.validateForm(errors)) {
        this.props.signupUser(this.state).then(
          response => {
            this.props.addFlashMessage({
              type: 'success',
              title: texts.signup_succeed_title,
              text: texts.signup_succeed_text
            });

            this.setState({
              username: '',
              email: '',
              password: '',
              confirmation: '',
              errors: {},
              isValid: false,
              isLoading: false
            });
          }, error => this.setState({errors: {form: error.response.data.message}})
        );
      } else {
        this.setState({errors: errors, isValid: false});
      }
    });
  }

  render() {
    const {isValid, isLoading, errors} = this.state;
    const {texts} = this.props;

    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert style='danger' title={texts.signup_failed_error_title} message={errors.form}
                          collapse={!!errors.form}/>

        <FormGroup controlId='username' validationState={!!errors.username ? 'error' : null}>
          <HelpBlock>{errors.username}</HelpBlock>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.username_form_control}
          </Col>
          <Col sm={8}>
            <FormControl name='username' type='text' value={this.state.username}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='email' validationState={!!errors.email ? 'error' : null}>
          <HelpBlock>{errors.email}</HelpBlock>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.email_form_control}
          </Col>
          <Col sm={8}>
            <FormControl name='email' type='email' value={this.state.email}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='password' validationState={!!errors.password ? 'error' : null}>
          <HelpBlock>{errors.password}</HelpBlock>
          <Col componentClass={ControlLabel} sm={4}>
            {texts.password_form_control}
          </Col>
          <Col sm={8}>
            <FormControl name='password' type='password' value={this.state.password}
                         onChange={this.onChange} required/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='confirmation' validationState={!!errors.confirmation ? 'error' : null}>
          <HelpBlock>{this.state.errors.confirmation}</HelpBlock>
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
          <Col smOffset={4} sm={4}>
            <a href='signin' className='pull-left'>{texts.signin_link_text}</a>
          </Col>
          <Col sm={4}>
            <Button type='submit' bsStyle='primary' className='pull-right' disabled={!isValid || isLoading}
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
  validateForm: PropTypes.func.isRequired,
  signupUser: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default SignupForm;