// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../state/selectors/language';
import {getIsAuthenticated} from '../../state/selectors/authentication';
import {
  requestAuthentication,
  signinUser
} from '../../state/queries/authentication';

import SigninButton from '../../components/SigninButton';
import StartApplicationButton from '../../components/StartApplicationButton';

require('../../styles/tbme-tv.css');
require('bootstrap/dist/css/bootstrap.min.css');

class App extends Component<App.propTypes> {
  componentWillMount() {
    requestAuthentication();
  }

  render() {
    const {isAuthenticated, texts} = this.props;

    return (
        <div className='intro-container'>
          <div className='intro-body d-flex align-items-center'>
            <div className='container justify-content-center text-center'>
              <h1 className='pb-4'>{texts.title}</h1>

              {
                isAuthenticated ?
                    <StartApplicationButton launchApplication={() => {
                    }} texts={texts}/> :
                    <SigninButton signinUser={signinUser} texts={texts}/>
              }
            </div>
          </div>
        </div>
    );
  }
}

App.propTypes = {
  isAuthenticated: PropTypes.bool.isRequired,
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    isAuthenticated: getIsAuthenticated(state),
    texts: getTexts(state).app
  }
}

export default connect(mapStateToProps)(App);
