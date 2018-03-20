// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';

import {addFlashMessage} from '../../../common/actions/flashMessageActions';
import {setProfile} from '../../actions/profileActions';

import loadProfile from '../../utils/loadProfile';

import Navbar from './Navbar';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  componentWillMount() {
    const {texts} = this.props;
    const {addFlashMessage, setProfile} = this.props.actions;

    loadProfile().then(
      response => setProfile(response.data),
      error => addFlashMessage({
        type: 'danger',
        title: texts.profile_fetch_failed_alert_title,
        text: texts.profile_fetch_failed_alert_text
      })
    )
  }

  render() {
    return (
      <div>
        <Navbar/>

        <div className='container'>
          <FlashMessageList/>
          {this.props.children}
        </div>
      </div>
    );
  }
}

App.propTypes = {
  actions: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}


function mapStateToProps(state) {
  return {
    texts: state.language.texts.app
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch),
      setProfile: bindActionCreators(setProfile, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(App);