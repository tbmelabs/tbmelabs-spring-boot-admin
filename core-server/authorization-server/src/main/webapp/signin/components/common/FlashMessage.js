'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import ReactTransitionGroup from 'react-addons-transition-group';

import Button from 'react-bootstrap/lib/Button';

import CollapsableAlert from './CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');

class FlashMessage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      collapse: false
    }

    this.onClick = this.onClick.bind(this);
  }

  componentDidMount() {
    this.setState({collapse: true});
  }

  // TODO: Replace low level js transition with high level css
  componentWillLeave(callback) {
    this.setState({collaps: false}, () => {
      setTimeout(callback, 300);
    });
  }

  onClick() {
    this.props.deleteFlashMessage(this.props.message.id);
  }

  render() {
    const {collapse} = this.state;
    const {type, title, text} = this.props.message;

    return (
      <ReactTransitionGroup component='div'>
        <CollapsableAlert collapse={collapse} style={type} title={title} message={' ' + text}>
          <Button onClick={this.onClick} bsClass='close'><span>&times;</span></Button>
        </CollapsableAlert>
      </ ReactTransitionGroup>
    );
  }
}

FlashMessage.propTypes = {
  message: PropTypes.object.isRequired,
  deleteFlashMessage: PropTypes.func.isRequired
}

export default FlashMessage;