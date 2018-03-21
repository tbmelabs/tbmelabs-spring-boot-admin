// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import {loadClients} from '../../actions/clientActions';
import {addFlashMessage} from '../../../common/actions/flashMessageActions';

import ClientList from '../../components/clients/ClientList';

class Clients extends Component <Clients.propTypes> {
  componentWillMount() {
    const {texts} = this.props;
    const {addFlashMessage, loadClients} = this.props.actions;

    loadClients().then(
      response => {
      },
      error => addFlashMessage({
        type: 'danger',
        title: texts.clients_fetch_failed_alert_title,
        text: texts.clients_fetch_failed_alert_text
      })
    )
  }

  render() {
    const {clients, texts} = this.props;

    return (
      <div>
        <ClientList clients={clients} texts={texts}/>
      </div>
    );
  }
}

Clients.propTypes = {
  actions: PropTypes.object.isRequired,
  clients: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    clients: state.clients,
    texts: state.language.texts.clients
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch),
      loadClients: bindActionCreators(loadClients, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Clients);