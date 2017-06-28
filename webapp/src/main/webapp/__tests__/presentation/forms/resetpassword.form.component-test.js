'use strict';

import React from 'react';

import TestUtils from 'react-test-utils/ReactTestUtils';
import expect from 'expect';

import ResetPasswordForm from '../../../app/components/ResetPassword';

describe('ResetPassword', () => {
  it('renders without problems', () => {
    var mount = TestUtils.renderIntoDocument(
      <ResetPassword/>
    );

    expect(mount).toExist();
  });
});