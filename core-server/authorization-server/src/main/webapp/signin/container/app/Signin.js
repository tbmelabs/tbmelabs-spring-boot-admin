'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {addFlashMessage} from '../../../common/actions/flashMessageActions';
import signin from '../../utils/signin';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';

import UsernamePasswordSigninForm from '../../components/signin/UsernamePasswordSigninForm';

require('../../styles/signin.css');

class Signup extends Component {
  render() {
    const {texts} = this.props;
    const {addFlashMessage} = this.props.actions;

    return (
      <div>
        <Jumbotron>
          <h1>{texts.jumbotron_title}</h1>
          <p>{texts.jumbotron_text}</p>
        </Jumbotron>

        <div className='signin-form'>
          <UsernamePasswordSigninForm signinUser={signin} addFlashMessage={addFlashMessage} texts={texts}/>
        </div>
      </div>
    );
  }
}

Signup.propTypes = {
  actions: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.signin
  }
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      addFlashMessage: bindActionCreators(addFlashMessage, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Signup);