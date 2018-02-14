'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

class UsernamePasswordSigninForm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: '',
      errors: {},
      isValid: false,
      isLoading: false
    }

    this.onChange = this.onChange.bind(this);
    this.validateForm = this.validateForm.bind(this);
    this.onSubmit = this.onSubmit.bind(this);

  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value}, this.validateForm);
  }

  validateForm() {
    this.setState({isValid: this.state.username && this.state.password});
  }

  onSubmit(event) {
    event.preventDefault();

    this.props.signinUser(this.state).then(
      response => window.location.replace(response.headers['no-redirect'])
      , error => this.setState({errors: {form: error.response.data.message}})
    );
  }

  render() {
    const {isValid, isLoading, errors} = this.state;
    const {texts} = this.props;

    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert style='danger' title={texts.signin_failed_alert_title}
                          message={texts.signin_failed_alert_text}
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

        <FormGroup className='link-group'>
          <Col smOffset={4} sm={4}>
            <a href='signup' className='pull-left'>{texts.signup_link_text}</a>
          </Col>
          <Col sm={4}>
            <Button type='submit' bsStyle='primary' className='pull-right' disabled={!isValid || isLoading}
                    onClick={isValid && !isLoading ? this.handleClick : null}>
              {isLoading ? texts.signin_button_loading_text : texts.signin_button_text}
            </Button>
          </Col>
        </FormGroup>
      </Form>
    );
  }
}

UsernamePasswordSigninForm.propTypes = {
  signinUser: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default UsernamePasswordSigninForm;