'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import  {connect} from 'react-redux';
import {login} from '../../actions/authActions'

import Alert from 'react-bootstrap/lib/Alert';
import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
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
        (res) => this.context.router.push('/'),
        (err) => this.setState({errors: err.response.data.errors, isLoading: false})
      );
    }
  }

  onChange(event) {
    this.setState({[e.target.name]: e.target.value});
  }

  render() {
    return (
      <Form onSubmit={this.onSubmit} horizontal>
        {this.state.errors.form && <Alert bsStyle='danger'>{this.state.errors.form}</Alert>}

        <FormGroup controlId='username'>
          <Col componentClass='ControlLabel' sm={2}>
            Username
          </Col>
          <Col sm={10}>
            <FormControl type='text' value={this.state.username} onChange={this.onChange}/>
          </Col>
          {this.state.errors.username && <HelpBlock>{this.state.errors.username}</HelpBlock>}
        </FormGroup>

        <FormGroup controlId='password'>
          <Col componentClass='ControlLabel' sm={2}>
            Password
          </Col>
          <Col sm={10}>
            <FormControl type='password' value={this.state.password} onChange={this.onChange}/>
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

export default connect(null, {login})(LoginForm);