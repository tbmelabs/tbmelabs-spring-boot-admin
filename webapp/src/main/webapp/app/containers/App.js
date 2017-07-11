'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators}from 'redux';
import {connect} from 'react-redux';

import {logout} from '../actions/authActions';
import {deleteFlashMessage} from '../actions/flashMessageActions';

import Navigation from '../components/Navigation';
import FlashMessageList from '../components/common/flashmessage/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends React.Component {
  render() {
    const {isAuthenticated, user} = this.props.auth;
    const {logout, deleteFlashMessage} = this.props.actions;

    return (
      <div>
        <Navigation isAuthenticated={isAuthenticated} user={user} logout={logout}/>
        <div className='container'>
          <FlashMessageList messages={this.props.messages} deleteFlashMessage={deleteFlashMessage}/>
          {this.props.children}
        </div>
      </div>
    );
  }
}

App.propTypes = {
  auth: PropTypes.object.isRequired,
  messages: PropTypes.array.isRequired,
  actions: PropTypes.object.isRequired
}

App.contextTypes = {
  router: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    auth: state.auth,
    messages: state.flashMessages
  };
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      logout: bindActionCreators(logout, dispatch),
      deleteFlashMessage: bindActionCreators(deleteFlashMessage, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(App);