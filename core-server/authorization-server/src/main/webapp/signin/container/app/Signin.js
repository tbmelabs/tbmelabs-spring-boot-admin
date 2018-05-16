// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {getTexts} from '../../state/selectors/language';
import {signinUser} from '../../state/queries/authentication';
import {addFlashMessage} from '../../state/queries/flashmessage';

import UsernamePasswordSigninForm from '../../components/signin/UsernamePasswordSigninForm';

require('../../styles/signin.css');

class Signin extends Component<Signin.propTypes> {
  render() {
    const {texts} = this.props;

    return (
        <div>
          <div className='signin-form'>
            <UsernamePasswordSigninForm signinUser={signinUser}
                                        addFlashMessage={addFlashMessage}
                                        texts={texts}/>
          </div>
        </div>
    );
  }
}

Signin.propTypes = {
  texts: PropTypes.object.isRequired
};

function mapStateToProps(state) {
  return {
    texts: getTexts(state).signin
  };
}

export default connect(mapStateToProps)(Signin);
