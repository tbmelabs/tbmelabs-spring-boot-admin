'use strict';

import React from 'react';

import TestUtils from 'react-test-utils/ReactTestUtils';
import expect from 'expect';

import Registration from '../../../app/containers/registration/Registration';

describe('Registration', () => {
  it('renders without problems', () => {
    var mount = TestUtils.renderIntoDocument(
      <Registration/>
    );

    expect(mount).toExist();
  });
});