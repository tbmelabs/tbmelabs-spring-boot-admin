// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../../state/selectors/language';
import {requestClientGrantTypes} from '../../../state/queries/grantType';
import {requestClientAuthorities} from '../../../state/queries/authority';
import {requestClientScopes} from '../../../state/queries/scope';
import {addFlashMessage} from '../../../state/queries/flashmessage';
import {saveClient} from '../../../state/queries/client';

import EditClientModal from '../../../components/clients/EditClientModal';

class ClientDialog extends Component<ClientDialog.propTypes> {
  render() {
    const {texts} = this.props;

    return (
        <EditClientModal loadGrantTypes={requestClientGrantTypes}
                         loadAuthorities={requestClientAuthorities}
                         loadScopes={requestClientScopes}
                         addFlashMessage={addFlashMessage}
                         saveClient={saveClient} texts={texts.modal}/>
    );
  }
}

ClientDialog.propTypes = {
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: getTexts(state).clients
  };
}

export default connect(mapStateToProps)(ClientDialog);
