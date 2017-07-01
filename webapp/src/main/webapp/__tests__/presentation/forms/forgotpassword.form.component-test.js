'use strict';

import React from 'react';

import TestUtils from 'react-test-utils/ReactTestUtils';
import expect from 'expect';

import ForgotPasswordForm from '../../../app/to_update/ForgotPassword';

describe('ForgotPassword', () => {
  it('renders without problems', () => {
    var mount = TestUtils.renderIntoDocument(
      <ForgotPassword/>
    );

    expect(mount).toExist();
  });
});