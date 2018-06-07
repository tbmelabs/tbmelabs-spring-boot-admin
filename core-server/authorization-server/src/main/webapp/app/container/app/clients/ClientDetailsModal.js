// @flow

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {type clientType} from '../../../../common/types/client.type';

import {getTexts} from '../../../state/selectors/language';
import {requestClient} from '../../../state/queries/client';

type ClientDetailsDialogState = {
  existingClient: clientType
}

class ClientDetailsModal extends Component<ClientDetailsModal.propTypes, ClientDetailsDialogState> {
  constructor(props: ClientDetailsModal.propTypes) {
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
    return (
        <h1>HEllo</h1>
    );
  }
}

ClientDetailsModal.propTypes = {
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: getTexts(state).clients
  };
}

export default connect(mapStateToProps)(ClientDetailsModal);