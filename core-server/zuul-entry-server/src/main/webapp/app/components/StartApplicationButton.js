// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

class StartApplicationButton extends Component<StartApplicationButton.propTypes> {
  render() {
    const {launchApplication, texts} = this.props;

    return (
        <button onClick={launchApplication} className='btn btn-transparent'>
          <span>{texts.launch}</span>
        </button>
    );
  }
}

StartApplicationButton.propTypes = {
  launchApplication: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default StartApplicationButton;
