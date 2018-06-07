// @flow

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

import {type clientType} from '../../../common/types/client.type';
import {type grantTypeType} from '../../../common/types/grantType.type';
import {type authorityType} from '../../../common/types/authority.type';
import {type scopeType} from '../../../common/types/scope.type';

import Modal from 'react-bootstrap/lib/Modal';
import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import Button from 'react-bootstrap/lib/Button';

require('bootstrap/dist/css/bootstrap.css');

type  ClientDetailsModalState = {
  ...clientType
}

class ClientDetailsModal extends Component <ClientDetailsModal.propTypes, ClientDetailsModalState> {
  constructor(props: ClientDetailsModal.propTypes,
      context: ClientDetailsModal.contextTypes) {
    super(props, context);

    this.state = {
      id: 0,
      created: 0,
      lastUpdated: 0,
      clientId: '',
      secret: '',
      isSecretRequired: false,
      isAutoApprove: false,
      accessTokenValiditySeconds: 0,
      refreshTokenValiditySeconds: 0,
      redirectUris: [],
      grantTypes: [],
      grantedAuthorities: [],
      scopes: [],
    };
  }

  componentWillReceiveProps(nextProps: EditClientModal.propTypes) {
    const {id} = this.state;
    const {existingClient} = nextProps;

    if (existingClient.id && !id) {
      this.setState({
        id: existingClient.id,
        created: existingClient.created,
        lastUpdated: existingClient.lastUpdated,
        clientId: existingClient.clientId,
        isSecretRequired: existingClient.isSecretRequired,
        isAutoApprove: existingClient.isAutoApprove,
        accessTokenValiditySeconds: existingClient.accessTokenValiditySeconds,
        refreshTokenValiditySeconds: existingClient.refreshTokenValiditySeconds,
        redirectUri: existingClient.redirectUris.join(';'),
        grantTypes: existingClient.grantTypes,
        authorities: existingClient.grantedAuthorities,
        scopes: existingClient.scopes,
      });
    }
  }

  render() {
    const {id, created, lastUpdated, clientId, isSecretRequired, isAutoApprove, accessTokenValiditySeconds, refreshTokenValiditySeconds, redirectUri, grantTypes, authorities, scopes} = this.state;
    const {texts} = this.props;

    if (!id) {
      return null;
    }

    return (
        <Modal.Dialog>
          <Modal.Header>
            <Modal.Title>{`${texts.client} ${id}`}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Grid>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.created}</Col>
                <Col sm={4}>{new Date(created).toDateString()}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.last_updated}</Col>
                <Col sm={4}>{new Date(lastUpdated).toDateString()}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.client_id}</Col>
                <Col sm={4}>{clientId}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.is_secret_required}</Col>
                <Col sm={4}>{isSecretRequired}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.is_auto_approve}</Col>
                <Col sm={4}>{isAutoApprove}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.access_token_validity}</Col>
                <Col sm={4}>{accessTokenValiditySeconds}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.refresh_token_validity}</Col>
                <Col sm={4}>{refreshTokenValiditySeconds}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.redirect_uri}</Col>
                <Col sm={4}>{redirectUri}</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.grant_types}</Col>
                <Col sm={4}>{
                  grantTypes
                  .map((grantType: grantTypeType) => grantType.name)
                  .join(', ')
                }</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.authorities}</Col>
                <Col sm={4}>{
                  authorities
                  .map((authority: authorityType) => authority.name)
                  .join(', ')
                }</Col>
              </Row>
              <Row>
                <Col className='text-heavy' sm={4}
                     smOffset={2}>{texts.scopes}</Col>
                <Col sm={4}>{
                  scopes
                  .map((scope: scopeType) => scope.name)
                  .join(', ')
                }</Col>
              </Row>
            </Grid>
          </Modal.Body>
          <Modal.Footer>
            <Button bsStyle='danger'
                    onClick={this.context.router.history.goBack}>{texts.modal.back_button_text}</Button>
            <Link to={`/clients/${id}/edit`}>
              <Button bsStyle='primary'>{texts.modal.edit_button_text}</Button>
            </Link>
          </Modal.Footer>
        </Modal.Dialog>
    );
  }
}

ClientDetailsModal.propTypes = {
  existingClient: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
};

ClientDetailsModal.contextTypes = {
  router: PropTypes.object.isRequired
};

export default ClientDetailsModal;