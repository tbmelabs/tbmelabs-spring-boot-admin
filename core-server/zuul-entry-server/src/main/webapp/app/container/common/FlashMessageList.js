// @flow
'use strict';

import React, {Component} from 'react';

import FlashMessage from '../../components/common/FlashMessage';

class FlashMessagesList extends Component<FlashMessagesList.propTypes> {
  render() {
    const messages = [];

    return (
      <div>
        {
          messages.map(message =>
            <FlashMessage key={message.id} message={message}
                          deleteFlashMessage={() => {
                          }}/>
          )
        }
      </div>
    );
  }
}

export default FlashMessagesList;