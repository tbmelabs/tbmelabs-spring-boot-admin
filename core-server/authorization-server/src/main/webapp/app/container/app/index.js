// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {requestProfile} from '../../state/queries/profile';

import Navbar from './Navbar';
import FlashMessageList from '../common/FlashMessageList';

require('bootstrap/dist/css/bootstrap.css');

class App extends Component<App.propTypes> {
  componentWillMount() {
    requestProfile();
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
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: state.language.texts.app
  };
}

export default connect(mapStateToProps)(App);
