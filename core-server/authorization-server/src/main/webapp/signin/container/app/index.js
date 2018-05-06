// @flow
'use strict';

import React, {Component} from 'react';

import {getTexts} from '../../state/queries/language';
import {addFlashMessage} from '../../state/queries/flashmessage';

import SigninJumbotron from '../../components/signin/SigninJumbotron';
import GoodbyeMessage from '../../components/messages/GoodbyeMessage';
import ConfirmationMessage from '../../components/messages/ConfirmationMessage';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  render() {
    const texts = getTexts('app');

    return (
        <div className='container'>
          <SigninJumbotron texts={texts}/>

          <GoodbyeMessage addFlashMessage={addFlashMessage}
                          texts={texts.flash_messages.logout}/>
          <ConfirmationMessage addFlashMessage={addFlashMessage}
                               texts={texts.flash_messages.confirmation}/>

          <FlashMessageList/>

          {this.props.children}
        </div>
    );
  }
}

export default App;
