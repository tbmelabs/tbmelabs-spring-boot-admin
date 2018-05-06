// @flow
'use strict';

import React, {Component} from 'react';

import {getTexts} from '../../state/queries/language';
import {addFlashMessage} from '../../state/queries/flashmessage';

import signin from '../../utils/signin';

import UsernamePasswordSigninForm from '../../components/signin/UsernamePasswordSigninForm';

require('../../styles/signin.css');

class Signup extends Component<Signup.propTypes> {
  render() {
    const texts = getTexts('signin');

    return (
        <div>
          <div className='signin-form'>
            <UsernamePasswordSigninForm signinUser={signin}
                                        addFlashMessage={addFlashMessage}
                                        texts={texts}/>
          </div>
        </div>
    );
  }
}

export default Signup;
