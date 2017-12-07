'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {authenticateUser} from '../../actions/authActions';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';

import UsernamePasswordLoginForm from '../../components/signin/UsernamePasswordLoginForm';

require('bootstrap/dist/css/bootstrap.css');
require('../../styles/signin.css');

class Signin extends Component {
  componentDidMount() {
    document.title = this.props.texts.tab_header;
  }

  render() {
    const {texts} = this.props;

    return (
      <div>
        <Jumbotron>
          <h1>{texts.jumbotron_title}</h1>
          <p>{texts.jumbotron_subtitle}</p>
        </Jumbotron>

        <div className='signin-form'>
          <UsernamePasswordLoginForm authenticateUser={authenticateUser} texts={texts}/>
        </div>
      </div>
    );
  }
}

Signin.propTypes = {
  texts: PropTypes.object.isRequired,
}

Signin.contextTypes = {
  router: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.signin
  }
}

export default connect(mapStateToProps, null)(Signin);