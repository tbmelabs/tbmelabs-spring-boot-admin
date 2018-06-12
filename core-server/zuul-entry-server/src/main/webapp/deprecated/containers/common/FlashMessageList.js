// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getFlashMessages} from '../../../app/state/selectors/flashmessage/index';
import {removeFlashMessage} from '../../../app/state/queries/flashmessage/index';

import FlashMessage from '../../components/common/FlashMessage';

class FlashMessagesList extends Component<FlashMessagesList.propTypes> {
  render() {
    const {flashMessages} = this.props;

    return (
        <div>
          {
            flashMessages.map(flashMessage =>
                <FlashMessage key={flashMessage.id} message={flashMessage}
                              deleteFlashMessage={removeFlashMessage}/>
            )
          }
        </div>
    );
  }
}

FlashMessagesList.propTypes = {
  flashMessages: PropTypes.array.isRequired
};

function mapStateToProps(state) {
  return {
    flashMessages: getFlashMessages(state)
  }
}

export default connect(mapStateToProps)(FlashMessagesList);
