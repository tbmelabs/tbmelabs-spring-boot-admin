// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

import {connect} from 'react-redux';

import {getClients} from '../../../state/selectors/client';
import {getTexts} from '../../../state/selectors/language';
import {requestClients} from '../../../state/queries/client';

import PageHeader from 'react-bootstrap/lib/PageHeader';
import Row from 'react-bootstrap/lib/Row';
import Button from 'react-bootstrap/lib/Button';

import ClientList from '../../../components/clients/ClientList';

require('../../../styles/clients.css');
require('bootstrap/dist/css/bootstrap.css');

class Clients extends Component <Clients.propTypes> {
  componentWillMount() {
    requestClients();
  }

  render() {
    const {clients, texts} = this.props;

    return (
        <div>
          <PageHeader>{texts.title}</PageHeader>

          <Row>
            <Link to='/clients/new'>
              <Button bsStyle='primary'
                      className='pull-right'>{texts.create_client_button}</Button>
            </Link>
          </Row>

          <Row>
            <ClientList clientPage={clients} texts={texts}/>
          </Row>
        </div>
    );
  }
}

Clients.propTypes = {
  clients: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    clients: getClients(state),
    texts: getTexts(state).clients
  };
}

export default connect(mapStateToProps)(Clients);
