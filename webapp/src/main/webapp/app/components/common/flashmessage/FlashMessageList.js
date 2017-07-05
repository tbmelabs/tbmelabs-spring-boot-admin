'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import FlashMessage from './FlashMessage';

class FlashMessagesList extends React.Component {
  render() {
    const messages = this.props.messages.map(message =>
      <FlashMessage key={message.id} message={message} deleteFlashMessage={this.props.deleteFlashMessage}/>
    );

    return (
      <div>{messages}</div>
    );
  }
}

FlashMessagesList.propTypes = {
  messages: PropTypes.array.isRequired,
  deleteFlashMessage: PropTypes.func.isRequired
}

export default FlashMessagesList;