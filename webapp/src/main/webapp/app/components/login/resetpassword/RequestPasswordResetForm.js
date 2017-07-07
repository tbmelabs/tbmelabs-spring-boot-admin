'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import CollapsableAlert from '../../common/alert/CollapsableAlert';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import ButtonToolbar from 'react-bootstrap/lib/ButtonToolbar';
import Button from 'react-bootstrap/lib/Button';

import validator from 'validator';
import validateInput from '../../../utils/validators/requestPasswordResetValidator';

require('bootstrap/dist/css/bootstrap.css');

class RequestPasswordResetForm extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      email: '',
      errors: {},
      isValid: false,
      isLoading: false
    };

    this.onCancel = this.onCancel.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onChange = this.onChange.bind(this);
  }

  componentWillMount() {
    if (this.props.email !== undefined && !validator.isEmpty(this.props.email)) {
      this.setState({email: this.props.email});
    }
  }

  isValid() {
    const {errors, isValid} = validateInput(this.state);

    if (!isValid) {
      this.setState({errors, isValid});
    }

    return isValid;
  }

  onCancel() {
    this.context.router.history.push('/login');
  }

  onSubmit(event) {
    event.preventDefault();

    if (this.isValid()) {
      this.setState({errors: {}, isLoading: true});

      this.props.requestPasswordReset(this.state).then(response => {
          this.props.addFlashMessage({
            type: 'success',
            text: 'An email has been sent to you if you own an account associated to the email given.'
          });

          this.context.router.history.push('/login');
        }, error => this.setState({errors: {form: error.response.data.message}, isLoading: false})
      );
    }
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value});
  }

  render() {
    const isValid = this.state.isLoading;
    const isLoading = this.state.isLoading;

    return (
      <Form onSubmit={this.handleSubmit} horizontal>
        <CollapsableAlert collapse={!!this.state.errors.form} style='danger' title='An error occurred: '
                          message={this.state.errors.form}/>

        <FormGroup controlId='email' validationState={!!this.state.errors.email ? 'error' : null}>
          <Col sm={2}>
            <ControlLabel>E-Mail</ControlLabel>
            <HelpBlock>{this.state.errors.email}</HelpBlock>
          </Col>

          <Col sm={10}>
            <FormControl name='email' type='text' value={this.state.email} onChange={this.onChange}/>
            <FormControl.Feedback />
          </Col>
        </FormGroup>

        <ButtonToolbar>
          <Button onClick={this.onCancel}>Cancel</Button>

          <Button type='submit' disabled={isLoading && !isValid}
                  onClick={!isLoading && isValid ? this.handleClick : null}>{isLoading ? 'Loading...' : 'Send Link'}</Button>
        </ButtonToolbar>
      </Form>
    );
  }
}

RequestPasswordResetForm.propTypes = {
  email: PropTypes.string,
  requestPasswordReset: PropTypes.func.isRequired,
  addFlashMessage: PropTypes.func.isRequired
}

RequestPasswordResetForm.contextTypes = {
  router: PropTypes.object.isRequired
}

export default RequestPasswordResetForm;