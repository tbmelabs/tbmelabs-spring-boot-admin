'use strict';

import React from 'react';
import TestUtils from 'react-dom/test-utils';

import {Provider} from 'react-redux';
import configureStore from '../app/configureStore';

import expect from 'expect';

import Router from '../app/Router';

const store = configureStore();

describe('Router', function () {
  it('renders without problems', function () {
    var router = TestUtils.renderIntoDocument(
      <Provider store={store}>
        <Router/>
      </Provider>
    );

    expect(router).toBeTruthy();
  });
});