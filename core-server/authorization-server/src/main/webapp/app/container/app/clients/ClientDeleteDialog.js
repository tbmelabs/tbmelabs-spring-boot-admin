// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {type clientType} from '../../../../common/types/client.type';

import {getTexts} from '../../../state/selectors/language';
import {deleteClient, requestClient} from '../../../state/queries/client';
import {addFlashMessage} from '../../../state/queries/flashmessage';

import DeleteClientDialog from '../../../components/clients/DeleteClientDialog';

type ClientDeleteDialogState = {
  existingClient: clientType
}

class ClientDeleteDialog extends Component<ClientDeleteDialog.propTypes, ClientDeleteDialogState> {
  constructor(props: ClientDialog.propTypes) {
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
  }

  render() {
    const {existingClient} = this.state;
    const {texts} = this.props;

    return (
        <DeleteClientDialog existingClient={existingClient}
                            addFlashMessage={addFlashMessage}
                            deleteClient={deleteClient} texts={texts}/>
    );
  }
}

ClientDeleteDialog.propTypes = {
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: getTexts(state).clients
  };
}

export default connect(mapStateToProps)(ClientDeleteDialog);
