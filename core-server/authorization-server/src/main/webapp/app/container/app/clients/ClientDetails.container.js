// @flow

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {type clientType} from '../../../../common/types/client.type';

import {getTexts} from '../../../state/selectors/language';
import {requestClient} from '../../../state/queries/client';

import ClientDetailsModal from '../../../components/clients/ClientDetailsModal';

type ClientDetailsContainerState = {
  existingClient: clientType
}

class ClientDetailsContainer extends Component<ClientDetailsContainer.propTypes, ClientDetailsContainerState> {
  constructor(props: ClientDetailsContainer.propTypes) {
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
        <ClientDetailsModal existingClient={existingClient} texts={texts}/>
    );
  }
}

ClientDetailsContainer.propTypes = {
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: getTexts(state).clients
  };
}

export default connect(mapStateToProps)(ClientDetailsContainer);