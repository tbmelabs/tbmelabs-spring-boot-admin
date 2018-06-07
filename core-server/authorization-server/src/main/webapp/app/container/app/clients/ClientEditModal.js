// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {type clientType} from '../../../../common/types/client.type';

import {getClientAuthorities} from '../../../state/selectors/authority';
import {getClientGrantTypes} from '../../../state/selectors/grantType';
import {getClientScopes} from '../../../state/selectors/scope';
import {getTexts} from '../../../state/selectors/language';
import {requestClientAuthorities} from '../../../state/queries/authority';
import {requestClientGrantTypes} from '../../../state/queries/grantType';
import {requestClientScopes} from '../../../state/queries/scope';
import {addFlashMessage} from '../../../state/queries/flashmessage';
import {
  requestClient,
  saveClient,
  updateClient
} from '../../../state/queries/client';

import NewClientModal from '../../../components/clients/NewClientModal';
import EditClientModal from "../../../components/clients/EditClientModal";

type ClientEditModalState = {
  existingClient: { ...clientType };
}

class ClientEditModal extends Component<ClientEditModal.propTypes, ClientEditModalState> {
  constructor(props: ClientEditModal.propTypes) {
    super(props);

    this.state = {
      existingClient: {}
    };
  }

  componentWillMount() {
    const {clientId} = this.props.match.params;
    if (clientId) {
      requestClient(clientId).then(
          response => this.setState({existingClient: response}));
    }

    requestClientAuthorities();
    requestClientGrantTypes();
    requestClientScopes();
  }

  render() {
    const {existingClient} = this.state;
    const {authorities, grantTypes, scopes, texts} = this.props;
    const {clientId} = this.props.match.params;

    if (!clientId) {
      return (
          <NewClientModal grantTypes={grantTypes}
                          authorities={authorities}
                          scopes={scopes}
                          addFlashMessage={addFlashMessage}
                          saveClient={saveClient} texts={texts}/>
      );
    }

    return (
        <EditClientModal existingClient={existingClient}
                         grantTypes={grantTypes}
                         authorities={authorities}
                         scopes={scopes}
                         addFlashMessage={addFlashMessage}
                         updateClient={updateClient} texts={texts}/>
    );
  }
}

ClientEditModal.propTypes = {
  authorities: PropTypes.array.isRequired,
  grantTypes: PropTypes.array.isRequired,
  scopes: PropTypes.array.isRequired,
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    authorities: getClientAuthorities(state),
    grantTypes: getClientGrantTypes(state),
    scopes: getClientScopes(state),
    texts: getTexts(state).clients
  };
}

export default connect(mapStateToProps)(ClientEditModal);
