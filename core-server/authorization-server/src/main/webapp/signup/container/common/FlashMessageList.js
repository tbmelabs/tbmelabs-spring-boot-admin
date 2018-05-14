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
    const {messages} = this.props;

    return (
        <div>
          {
            messages.map(message =>
                <FlashMessage key={message.id} message={message}
                              deleteFlashMessage={removeFlashMessage}/>
            )
          }
        </div>
    );
  }
}

FlashMessagesList.propTypes = {
  messages: PropTypes.array.isRequired
}

function mapStateToProps(state) {
  return {
    messages: getFlashMessages(state)
  }
}

export default connect(mapStateToProps)(FlashMessagesList);
