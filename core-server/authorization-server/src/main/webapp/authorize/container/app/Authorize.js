// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {parse} from 'query-string';

import {getClient} from '../../state/selectors/client';
import {getTexts} from '../../state/selectors/language';
import {requestClientScopes} from '../../state/queries/client';

import ClientApprovalForm from '../../components/authorize/ClientApprovalForm';

require('../../styles/authorize.css');

class Authorize extends Component<Authorize.propTypes> {
  componentWillMount() {
    requestClientScopes(parse(window.location.search.substr(1)).client_id);
  }

  render() {
    const {client, texts} = this.props;

    return (
        <div className='approval-form'>
          <ClientApprovalForm clientId={client.clientId} scopes={client.scopes}
                              texts={texts}/>
        </div>
    );
  }
}

Authorize.propTypes = {
  client: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    client: getClient(state),
    texts: getTexts(state).authorize
  };
}

export default connect(mapStateToProps)(Authorize);
