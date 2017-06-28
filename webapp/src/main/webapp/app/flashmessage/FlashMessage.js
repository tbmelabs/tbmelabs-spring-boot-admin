'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import Alert from 'react-bootstrap/lib/Alert';
import Button from 'react-bootstrap/lib/Button';

require('bootstrap/dist/css/bootstrap.css');

class FlashMessage extends React.Component {
  constructor(props) {
    super(props);

    this.onClick = this.onClick.bind(this);
  }

  onClick() {
    this.props.deleteFlashMessage(this.props.message.id);
  }

  render() {
    const {id, type, title, text} = this.props.message;
    return (
      <Alert bsStyle={type}>
        <strong>{title}</strong> {text}
        <Button onClick={this.onClick} bsClass='close'><span>&times;</span></Button>
      </Alert>
    );
  }
}

FlashMessage.propTypes = {
  message: PropTypes.object.isRequired,
  deleteFlashMessage: PropTypes.func.isRequired
}

export default FlashMessage;