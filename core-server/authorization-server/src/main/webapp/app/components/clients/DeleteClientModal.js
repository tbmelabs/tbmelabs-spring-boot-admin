// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {addSaga, removeSaga} from '../../state/SagaManager';
import {UPDATE_CLIENT_SUCCEED} from '../../state/actions/client';

import Modal from 'react-bootstrap/lib/Modal';
import Button from 'react-bootstrap/lib/Button';

require('bootstrap/dist/css/bootstrap.css');

type EditClientModalState = {
  id: number;
  // clientId: string;
  // secret?: string;
  // accessTokenValiditySeconds: string;
  // refreshTokenValiditySeconds: string;
  // redirectUri: string;
  // grantTypes: grantTypeType[];
  // authorities: authorityType[];
  // scopes: scopeType[];
  closeSagaId: string;
}

class EditClientModal extends Component<EditClientModal.propTypes, EditClientModalState> {
  onSubmit: () => void;

  constructor(props: EditClientModal.propTypes,
      context: EditClientModal.contextTypes) {
    super(props, context);

    const {existingClient} = props;

    this.state = {
      id: 0,
      // clientId: '',
      // secret: '',
      // accessTokenValiditySeconds: '',
      // refreshTokenValiditySeconds: '',
      // redirectUri: '',
      // grantTypes: [],
      // authorities: [],
      // scopes: [],
      closeSagaId: addSaga(UPDATE_CLIENT_SUCCEED,
          context.router.history.goBack)
    };

    this.onSubmit = this.onSubmit.bind(this);
  }

  componentWillReceiveProps(nextProps: EditClientModal.propTypes) {
    const {id} = this.state;
    const {existingClient} = nextProps;

    if (existingClient.id && !id) {
      this.setState({
        id: existingClient.id,
        // clientId: existingClient.clientId,
        // secret: existingClient.secret,
        // accessTokenValiditySeconds: existingClient.accessTokenValiditySeconds,
        // refreshTokenValiditySeconds: existingClient.refreshTokenValiditySeconds,
        // redirectUri: existingClient.redirectUris.join(';'),
        // grantTypes: existingClient.grantTypes,
        // authorities: existingClient.grantedAuthorities,
        // scopes: existingClient.scopes,
      });
    }
  }

  componentWillUnmount() {
    removeSaga(this.state.closeSagaId);
  }

  onSubmit(event: SyntheticInputEvent<HTMLInputElement>) {
    event.preventDefault();

    const {id, clientId, secret, accessTokenValiditySeconds, refreshTokenValiditySeconds, redirectUri, grantTypes, authorities, scopes, isValid} = this.state;

    this.props.deleteClient({
      id: id,
      // clientId: clientId,
      // secret: secret,
      // accessTokenValiditySeconds: accessTokenValiditySeconds,
      // refreshTokenValiditySeconds: refreshTokenValiditySeconds,
      // redirectUris: redirectUri.split(';'),
      // grantTypes: grantTypes,
      // grantedAuthorities: authorities,
      // scopes: scopes
    });
  }

  render() {
    const {id} = this.state;
    const {texts} = this.props;

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
            <Button bsStyle='primary' onClick={this.onSubmit}>{texts.modal.delete_button_text}</Button>
          </Modal.Footer>
        </Modal.Dialog>
    );
  }
}

EditClientModal.propTypes = {
  existingClient: PropTypes.object.isRequired,
  addFlashMessage: PropTypes.func.isRequired,
  deleteClient: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
};

EditClientModal.contextTypes = {
  router: PropTypes.object.isRequired
};

export default EditClientModal;
