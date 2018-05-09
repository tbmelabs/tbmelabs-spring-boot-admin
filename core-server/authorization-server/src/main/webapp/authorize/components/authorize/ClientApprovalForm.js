// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import Form from 'react-bootstrap/lib/Form';
import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import Radio from 'react-bootstrap/lib/Radio';
import Button from 'react-bootstrap/lib/Button';

require('bootstrap/dist/css/bootstrap.css');

const CLIENT_PLACEHOLDER = '[CLIENT_ID]';

class ClientApprovalForm extends Component<ClientApprovalForm.propTypes> {
  render() {
    const {clientId, scopes, texts} = this.props;

    return (
        <Form id='confirmationForm' name='confirmationForm' action='authorize'
              method='post' horizontal>
          {/*TODO: ClientId is not the most beautiful thing here.. maybe add client name?*/}
          <h1>{texts.approve_title_question.replace(CLIENT_PLACEHOLDER, '\''
              + clientId + '\'')}</h1>

          <FormControl name='user_oauth_approval' value='true' type='hidden'/>

          {
            scopes.map(scope => {
              return (
                  <FormGroup key={scope}>
                    <Col componentClass={ControlLabel} sm={4}>
                      {scope}
                    </Col>
                    <Col sm={8}>
                      <Radio name={'scope.' + scope} value='true' inline
                             defaultChecked>
                        {texts.toggle_approve}
                      </Radio>
                      <Radio name={'scope.' + scope} value='false' inline>
                        {texts.toggle_deny}
                      </Radio>
                    </Col>
                  </FormGroup>
              );
            })
          }

          <FormGroup className='link-group'>
            <Col smOffset={8} sm={4}>
              <Button name='authorize' value='Authorize' type='submit'
                      bsStyle='primary' className='pull-right'>
                {texts.approve_button_text}
              </Button>
            </Col>
          </FormGroup>
        </Form>
    );
  }
}

ClientApprovalForm.propTypes = {
  clientId: PropTypes.string.isRequired,
  scopes: PropTypes.array.isRequired,
  texts: PropTypes.array.isRequired
}

export default ClientApprovalForm;
