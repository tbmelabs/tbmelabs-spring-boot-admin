// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../state/selectors/language';
import {signupUser} from '../../state/queries/signup';
import {addFlashMessage} from '../../state/queries/flashmessage';

import validateSignupForm from '../../utils/validateSignupForm';

import SignupForm from '../../components/signup/SignupForm';

require('../../styles/signup.css');

class Signup extends Component<Signup.propTypes> {
  render() {
    const {texts} = this.props;

    return (
        <div>
          <div className='signup-form'>
            <SignupForm validateForm={validateSignupForm}
                        signupUser={signupUser}
                        addFlashMessage={addFlashMessage}
                        texts={texts}/>
          </div>
        </div>
    );
  }
}

Signup.propTypes = {
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: getTexts(state).signup
  };
}

export default connect(mapStateToProps)(Signup);
