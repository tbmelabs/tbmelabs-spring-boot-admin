'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import Fade from 'react-bootstrap/lib/Fade';
import Alert from 'react-bootstrap/lib/Alert';

require('bootstrap/dist/css/bootstrap.css');

class FadableAlert extends React.Component {
  render() {
    return (
      <Fade in={this.props.fade}>
        <Alert bsStyle={this.props.style}>
          <strong>{this.props.title}</strong>
          {this.props.message}
        </Alert>
      </Fade>
    );
  }
}

FadableAlert.propTypes = {
  fade: PropTypes.bool.isRequired,
  style: PropTypes.string.isRequired,
  title: PropTypes.string,
  message: PropTypes.string
}

export default FadableAlert;