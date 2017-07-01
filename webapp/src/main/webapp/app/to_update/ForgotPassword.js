'use strict';

import React from 'react';

import axios from 'axios';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import Button from 'react-bootstrap/lib/Button';

import CustomAlert from './CustomAlert';

require('bootstrap/dist/css/bootstrap.css');

class ForgotPassword extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            email: '',
            submitted: false
        };

        this.emailChanged = this.emailChanged.bind(this);

        this.submitButtonDisabled = this.submitButtonDisabled.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    emailChanged(event) {
        this.setState({email: event.target.value});
    }

    submitButtonDisabled() {
        return this.state.email === '';
    }

    handleSubmit(event) {
        event.preventDefault();

        var self = this;

        axios.post('/login/reset-password', {email: this.state.email})
            .then(response => {
                this.setState({submitted: true});
            });
    }

    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                {this.state.submitted === true ?
                    <CustomAlert type='info' title='Reset started:'
                                 message='An email has been sent to you if you own an account associated with the given email!'/> :
                    <CustomAlert className='invisible'/>
                }

                <FormGroup controlId='formAccountEmail'>
                    <ControlLabel> E-Mail Address: <FormControl type='email' value={this.state.email}
                                                                onChange={this.emailChanged}/>
                    </ControlLabel>
                </FormGroup>

                <FormControl.Feedback />

                <Button type='submit' disabled={this.submitButtonDisabled()}>Submit</Button>
            </Form>
        );
    }
}

export default ForgotPassword;