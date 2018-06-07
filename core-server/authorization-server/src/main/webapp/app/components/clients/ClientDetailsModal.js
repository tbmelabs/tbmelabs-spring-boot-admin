// @flow

import React, {Component} from 'react';
import PropTypes from 'prop-types';
import type {authorityType} from '../../../common/types/authority.type';
import type {scopeType} from '../../../common/types/scope.type';
import type {grantTypeType} from '../../../common/types/grantType.type';
import EditClientModal from './EditClientModal';

type ClientDetailsModalState = {
  id: number;
  clientId: string;
  secret?: string;
  accessTokenValiditySeconds: string;
  refreshTokenValiditySeconds: string;
  redirectUri: string;
  grantTypes: grantTypeType[];
  authorities: authorityType[];
  scopes: scopeType[];
}

class ClientDetailsModal extends Component <ClientDetailsModal.propTypes> {
  constructor(props: ClientDetailsModal.propTypes,
      context: ClientDetailsModal.contextTypes) {
    super(props, context);

    this.state = {
      id: 0,
      clientId: '',
      secret: '',
      accessTokenValiditySeconds: '',
      refreshTokenValiditySeconds: '',
      redirectUri: '',
      grantTypes: [],
      authorities: [],
      scopes: []
    };
  }

  componentWillReceiveProps(nextProps: EditClientModal.propTypes) {
    const {id} = this.state;
    const {existingClient} = nextProps;

    if (existingClient.id && !id) {
      this.setState({
        id: existingClient.id,
        clientId: existingClient.clientId,
        secret: existingClient.secret,
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
    const {id, clientId, secret, accessTokenValiditySeconds, refreshTokenValiditySeconds, grantTypes, authorities, scopes} = this.state;

    if (!id) {
      return null;
    }

    return (
        <Modal.Dialog>
          <Modal.Header>
            <Modal.Title>{texts.modal.delete_title}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {`${texts.modal.delete_text}${id}?`}
          </Modal.Body>
          <Modal.Footer>
            <Button bsStyle='danger'
                    onClick={this.context.router.history.goBack}>{texts.modal.cancel_button_text}</Button>
            <Button bsStyle='primary'
                    onClick={this.onSubmit}>{texts.modal.delete_button_text}</Button>
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