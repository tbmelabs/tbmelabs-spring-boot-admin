'use strict';

import React from 'react';

import {shallow} from 'enzyme';
import {expect} from 'chai';

import CollapsableAlert from '../../../app/components/common/CollapsableAlert';

describe('CollapsableAlert', function () {
  const STYLE_SUCCESS = 'success';
  const STYLE_INFO = 'info';
  const STYLE_WARNING = 'warning';
  const STYLE_ERROR = 'error';

  it('renders success-message without problems', function () {
    var router = shallow(
      <CollapsableAlert collapse={false} style={STYLE_SUCCESS}/>
    );

    expect(router).to.be.ok;
  });
});