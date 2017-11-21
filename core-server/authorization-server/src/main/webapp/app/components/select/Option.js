'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import Panel from 'react-bootstrap/lib/Panel';

require('bootstrap/dist/css/bootstrap.css');

class Option extends Component {
  constructor(props) {
    super(props);

    this.state = {
      refreshTokenQuery: '?refresh_token='
    }

    this.onClick = this.onClick.bind(this);
  }

  onClick(url) {
    window.location.assign(url);
  }

  render() {
    const {refreshTokenQuery} = this.state;
    const {name, link, user} = this.props;

    const composedUrl = link + refreshTokenQuery + user.refresh_token;

    return (
      <Panel className='option' onClick={() => this.onClick(composedUrl)}>
        {name}
      </Panel>
    );
  }
}

Option.propTypes = {
  name: PropTypes.string.isRequired,
  link: PropTypes.string.isRequired,
  user: PropTypes.object.isRequired
}

export default Option;