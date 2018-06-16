// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

require('../styles/components/btn-transparent.css');

class TransparentButton extends Component<TransparentButton.propTypes> {
  render() {
    const {action, lable} = this.props;

    return (
        <button onClick={action} className='btn btn-transparent'>
          <span>{lable}</span>
        </button>
    );
  }
}

TransparentButton.propTypes = {
  action: PropTypes.func.isRequired,
  lable: PropTypes.object.isRequired
}

export default TransparentButton;
