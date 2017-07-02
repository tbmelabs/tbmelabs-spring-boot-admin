'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import CollapsableAlert from '../../components/common/alert/CollapsableAlert';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

import validateInput from '../../utils/validators/login'

require('bootstrap/dist/css/bootstrap.css');

class LoginForm extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: '',
      errors: {},
      isLoading: false
    };

    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
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

      this.props.login(this.state).then(
        (response) => this.context.router.push('/'),
        (error) => this.setState({errors: {form: error.response.data.message}, isLoading: false})
      );
    }
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value});
  }

  render() {
    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert collapse={this.state.errors.form} style='danger' message={this.state.errors.form}/>

        <FormGroup controlId='username'>
          <Col componentClass='ControlLabel' sm={2}>
            Username
          </Col>
          <Col sm={10}>
            <FormControl name='username' type='text' value={this.state.username} onChange={this.onChange}/>
          </Col>
          {this.state.errors.username && <HelpBlock>{this.state.errors.username}</HelpBlock>}
        </FormGroup>

        <FormGroup controlId='password'>
          <Col componentClass='ControlLabel' sm={2}>
            Password
          </Col>
          <Col sm={10}>
            <FormControl name='password' type='password' value={this.state.password} onChange={this.onChange}/>
          </Col>
          {this.state.errors.password && <HelpBlock>{this.state.errors.password}</HelpBlock>}
        </FormGroup>

        <FormControl.Feedback/>

        <Button type='submit'>Login</Button>
      </Form>
    );
  }
}

LoginForm.propTypes = {
  login: PropTypes.func.isRequired
}

LoginForm.contextTypes = {
  router: PropTypes.object.isRequired
}

export default LoginForm;