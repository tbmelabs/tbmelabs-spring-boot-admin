'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {addFlashMessage} from '../../actions/flashMessageActions';
import {validateSignupForm, signupUser} from '../../actions/signupActions';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';

import SignupForm from '../../components/signup/SignupForm';

require('bootstrap/dist/css/bootstrap.css');
require('../../styles/signup.css');

class Signup extends Component {
  componentWillMount() {
    if (!require('../../config/config.json').isRegistrationEnabled) {
      this.context.router.history.push('/');
    }
  }

  componentDidMount() {
    document.title = this.props.texts.tab_header;
  }

  render() {
    const {texts} = this.props;
    const {addFlashMessage} = this.props.actions;

    return (
      <div>
        <Jumbotron>
          <h1>{texts.jumbotron_title}</h1>
        </Jumbotron>

        <div className='signup-form'>
          <SignupForm validateForm={validateSignupForm} signupUser={signupUser} addFlashMessage={addFlashMessage}
                      texts={texts}/>
        </div>
      </div>
    );
  }
}

Signup.propTypes = {
  actions: PropTypes.object.isRequired,
  texts: PropTypes.object.isRequired
}

Signup.contextTypes = {
  router: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.signup
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