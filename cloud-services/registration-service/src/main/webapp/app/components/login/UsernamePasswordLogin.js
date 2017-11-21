'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from '../common/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

class UsernamePasswordLogin extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: '',
      password: '',
      errors: {},
      isLoading: false
    };

    this.onChange = this.onChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value});
  }

  onSubmit(event) {
    event.preventDefault();

    this.setState({errors: {}, isLoading: true});

    this.props.authenticateUser(this.state, (response) => {
      this.setState({errors: {}, isLoading: false});

      window.location.assign(this.props.redirectUrl + '?access_token=' + response.data.access_token);
    }, (error) => {
      this.setState({errors: {form: error.response.data.message}, isLoading: false});
    });
  }

  render() {
    const {isLoading} = this.state;

    return (
      <Form onSubmit={this.onSubmit} horizontal>
        <CollapsableAlert style='danger' title='Login failed: ' message={this.state.errors.form}
                          collapse={!!this.state.errors.form}/>

        <FormGroup controlId='username'>
          <Col componentClass={ControlLabel}>
            Username
          </Col>
          <Col>
            <FormControl name='username' type='text' value={this.state.username} onChange={this.onChange}/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <FormGroup controlId='password'>
          <Col componentClass={ControlLabel}>
            Password
          </Col>
          <Col>
            <FormControl name='password' type='password' value={this.state.password}
                         onChange={this.onChange}/>
            <FormControl.Feedback/>
          </Col>
        </FormGroup>

        <Button className='pull-right' type='submit' disabled={isLoading}
                onClick={!isLoading ? this.handleClick : null}>{isLoading ? 'Loading...' : 'Sign In'}</Button>
      </Form>
    );
  }
}

UsernamePasswordLogin.propTypes = {
  authenticateUser: PropTypes.func.isRequired,
  redirectUrl: PropTypes.string.isRequired
}

export default UsernamePasswordLogin;