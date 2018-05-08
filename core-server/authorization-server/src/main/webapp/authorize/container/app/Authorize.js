// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../state/selectors/language';
import {requestClientScopes} from '../../state/queries/client';

import ClientApprovalForm from '../../components/authorize/ClientApprovalForm';

require('../../styles/authorize.css');

class Authorize extends Component<Authorize.propTypes> {
  render() {
    const {texts} = this.props;

    return (
        <div className='approval-form'>
          <ClientApprovalForm getClientApprovals={requestClientScopes}
                              texts={texts}/>
        </div>
    );
  }
}

Authorize.propTypes = {
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: getTexts(state)['authorize']
  }
}

export default connect(mapStateToProps)(Authorize);
