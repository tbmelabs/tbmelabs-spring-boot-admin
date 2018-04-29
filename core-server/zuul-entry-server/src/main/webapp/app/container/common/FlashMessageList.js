// @flow
'use strict';

import React, {Component} from 'react';

import FlashMessageQueries from '../../queries/flashMessage.queries';

import FlashMessage from '../../components/common/FlashMessage';

class FlashMessagesList extends Component<FlashMessagesList.propTypes> {
  render() {
    const messages = FlashMessageQueries.getFlashMessages();

    return (
      <div>
        {
          messages.map(message =>
            <FlashMessage key={message.id} message={message}
                          deleteFlashMessage={FlashMessageQueries.removeFlashMessage}/>
          )
        }
      </div>
    );
  }
}

export default FlashMessagesList;