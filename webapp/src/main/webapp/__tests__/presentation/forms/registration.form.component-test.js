'use strict';

import React from 'react';
import TestUtils from 'react-test-utils/ReactTestUtils';

import Registration from '../../../app/containers/registration/Registration';

describe('Registration', () => {
  it('renders without problems', () => {
    var registrationForm = TestUtils.renderIntoDocument(
      <Registration actions={{
        registerUser: ()=> {
          console.log('registerUser()');
        },
        addFlashMessage: ()=> {
          console.log('addFlashMessage()');
        },
        isUsernameUnique: ()=> {
          console.log('isUsernameUnique()');
        },
        isEmailUnique: ()=> {
          console.log('isEmailUnique()');
        },
        doesPasswordMatchFormat: ()=> {
          console.log('doesPasswordMatchFormat()');
        },
        doPasswordsMatch: ()=> {
          console.log('doPasswordMatch()');
        }
      }}/>
    );

    expect(registrationForm).toExist();
  });
});