// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

import isEmpty from 'lodash/isEmpty';

import {ENTITY_ARRAY_JOIN_SEPARATOR} from '../../config';

import {type clientType} from '../../../common/types/client.type';
import {type grantTypeType} from '../../../common/types/grantType.type';
import {type authorityType} from '../../../common/types/authority.type';
import {type scopeType} from '../../../common/types/scope.type';

import Table from 'react-bootstrap/lib/Table';
import Glyphicon from 'react-bootstrap/lib/Glyphicon';

import CollapsableAlert from '../../../common/components/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

class ClientList extends Component<ClientList.propTypes> {
  render() {
    const {clients, texts} = this.props;

    if (isEmpty(clients)) {
      return (
          <CollapsableAlert collapse={true} style='warning'
                            title={texts.errors.no_clients_alert_title}
                            message={texts.errors.no_clients_alert_text}/>
      );
    } else {
      return (
          <div>
            <Table hover responsive>
              <thead>
              <th>{texts.id}</th>
              <th>{texts.created}</th>
              <th>{texts.last_updated}</th>
              <th>{texts.client_id}</th>
              <th>{texts.redirect_uri}</th>
              <th>{texts.grant_types}</th>
              <th>{texts.authorities}</th>
              <th>{texts.scopes}</th>
              <th></th>
              </thead>
              <tbody>
              {
                clients.map((client: clientType) =>
                    <tr key={`client-${client.id}`}>
                      <td>{client.id}</td>
                      <td>{client.created}</td>
                      <td>{client.lastUpdated}</td>
                      <td>{client.clientId}</td>
                      <td>{client.redirectUris
                      .join(ENTITY_ARRAY_JOIN_SEPARATOR)}</td>
                      <td>{client.grantTypes
                      .flatMap((grantType: grantTypeType) => grantType.name)
                      .join(ENTITY_ARRAY_JOIN_SEPARATOR)}</td>
                      <td>{client.grantedAuthorities
                      .flatMap((authority: authorityType) => authority.name)
                      .join(ENTITY_ARRAY_JOIN_SEPARATOR)}</td>
                      <td>{client.scopes
                      .flatMap((scope: scopeType) => scope.name)
                      .join(ENTITY_ARRAY_JOIN_SEPARATOR)}</td>
                      <td>
                        <Link to={`/clients/${client.id}`}>
                          <Glyphicon glyph='zoom-in'/>
                        </Link>
                        <Link to={`/clients/${client.id}/edit`}>
                          <Glyphicon glyph='pencil'/>
                        </Link>
                      </td>
                    </tr>
                )
              }
              </tbody>
            </Table>
          </div>
      );
    }
  }
}

ClientList.propTypes = {
  clients: PropTypes.array.isRequired,
  texts: PropTypes.object.isRequired
};

export default ClientList;
