'use strict';

import React from 'react';
import TestUtils from 'react-test-utils/ReactTestUtils';

import Login from '../../../app/containers/login/Login';

describe('Login', () => {
  it('renders without problems', () => {
    const loginForm = TestUtils.renderIntoDocument(
      <Login login={()=> {
        console.log('login()');
      }}/>
    );

    expect(loginForm).toExist();
  });
});