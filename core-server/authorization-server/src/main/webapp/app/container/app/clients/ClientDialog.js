// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getClientAuthorities} from '../../../state/selectors/authority';
import {getClientGrantTypes} from '../../../state/selectors/grantType';
import {getClientScopes} from '../../../state/selectors/scope';
import {getTexts} from '../../../state/selectors/language';
import {requestClientAuthorities} from '../../../state/queries/authority';
import {requestClientGrantTypes} from '../../../state/queries/grantType';
import {requestClientScopes} from '../../../state/queries/scope';
import {addFlashMessage} from '../../../state/queries/flashmessage';
import {saveClient} from '../../../state/queries/client';

import EditClientModal from '../../../components/clients/EditClientModal';

class ClientDialog extends Component<ClientDialog.propTypes> {
  componentWillMount() {
    requestClientAuthorities();
    requestClientGrantTypes();
    requestClientScopes();
  }

  render() {
    const {authorities, grantTypes, scopes, texts} = this.props;

    return (
        <EditClientModal authorities={authorities}
                         grantTypes={grantTypes}
                         scopes={scopes}
                         addFlashMessage={addFlashMessage}
                         saveClient={saveClient} texts={texts.modal}/>
    );
  }
}

ClientDialog.propTypes = {
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

export default connect(mapStateToProps)(ClientDialog);
