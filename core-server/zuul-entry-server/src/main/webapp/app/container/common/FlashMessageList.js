// @flow
'use strict';

import React, {Component} from 'react';

import {getFlashMessages, removeFlashMessage} from '../../state/queries/flashmessage'

import FlashMessage from '../../components/common/FlashMessage';

class FlashMessagesList extends Component<FlashMessagesList.propTypes> {
  render() {
    const messages = getFlashMessages();

    return (
      <div>
        {
          messages.map(message =>
            <FlashMessage key={message.id} message={message} deleteFlashMessage={removeFlashMessage}/>
          )
        }
      </div>
    );
  }
}

export default FlashMessagesList;
