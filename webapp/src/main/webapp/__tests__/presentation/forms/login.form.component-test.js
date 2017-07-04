'use strict';

import React from 'react';

import TestUtils from 'react-test-utils/ReactTestUtils';
import expect from 'expect';

import Login from '../../../app/containers/login/Login';

describe('Login', () => {
  it('renders without problems', () => {
    var mount = TestUtils.renderIntoDocument(
      <Login/>
    );

    expect(mount).toExist();
  });
});