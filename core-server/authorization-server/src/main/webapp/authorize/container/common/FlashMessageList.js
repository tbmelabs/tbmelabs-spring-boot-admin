// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getFlashMessages} from '../../state/selectors/flashmessage';
import {removeFlashMessage} from '../../state/queries/flashmessage';

import FlashMessage from '../../../common/components/FlashMessage';

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
}

function mapStateToProps(state) {
  return {
    flashMessages: getFlashMessages(state)
  }
}

export default connect(mapStateToProps)(FlashMessagesList);
