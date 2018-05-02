// @flow
'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {Route} from 'react-router';

class PreConditionalRoute extends Route {
  render() {
    const {onEnter, shouldMount, onShouldMountError} = this.props;

    if (onEnter !== undefined) {
      onEnter();
    }

    if (shouldMount !== undefined && onShouldMountError === undefined) {
      throw new SyntaxError(
          'Cannot use #shouldMount() without providing an #onShouldMountError() function!');
    }

    if (shouldMount !== undefined && !shouldMount()) {
      onShouldMountError();
    } else {
      return super.render();
    }

    return null;
  }
}

PreConditionalRoute.propTypes = {
  onEnter: PropTypes.func,
  shouldMount: PropTypes.func,
  onShouldMountError: PropTypes.func
};

export default PreConditionalRoute;
