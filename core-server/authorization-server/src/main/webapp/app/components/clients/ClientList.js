// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

class ClientList extends Component<ClientList.propTypes> {
  render() {
    return (<h1>Client List</h1>);
  }
}

ClientList.propTypes = {
  clients: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

export default ClientList;