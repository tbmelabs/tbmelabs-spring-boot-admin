'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';

require('../../styles/account-information.css');
require('bootstrap/dist/css/bootstrap.css');

class AccountInformation extends Component {
  render() {
    const {account, texts} = this.props;

    return (
      <div>
        <h1>Welcome, {account.username}</h1>

        <Grid>
          <Row>
            <Col className='text-heavy'>{texts.information.created}</Col>
            <Col>{account.created}</Col>
          </Row>
          <Row>
            <Col className='text-heavy'>{texts.information.username}</Col>
            <Col>{account.username}</Col>
          </Row>
          <Row>
            <Col className='text-heavy'>{texts.information.email}</Col>
            <Col>{account.email}</Col>
          </Row>
        </Grid>
      </div>
    );
  }
}

AccountInformation.propTypes = {
  account: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired,
}

export default AccountInformation;