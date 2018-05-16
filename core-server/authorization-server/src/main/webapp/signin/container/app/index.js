// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../state/selectors/language';

import addConfirmationMessageIfRequested from '../../utils/messages/addConfirmationMessageIfRequested';
import addGoodbyeMessageIfRequested from '../../utils/messages/addGoodbyeMessageIfRequested';
import addSignupMessageIfRequested from '../../utils/messages/addSignupMessageIfRequested';

import SigninJumbotron from '../../components/signin/SigninJumbotron';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  componentWillMount() {
    addConfirmationMessageIfRequested();
    addGoodbyeMessageIfRequested();
    addSignupMessageIfRequested();
  }

  render() {
    const {texts} = this.props;

    return (
        <div className='container'>
          <SigninJumbotron texts={texts}/>

          <FlashMessageList/>

          {this.props.children}
        </div>
    );
  }
}

App.propTypes = {
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: getTexts(state).app
  };
}

export default connect(mapStateToProps)(App);
