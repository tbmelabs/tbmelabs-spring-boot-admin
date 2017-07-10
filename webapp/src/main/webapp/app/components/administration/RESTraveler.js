'use strict';

import React from 'react';
import PropTypes from 'prop-types';

require('bootstrap/dist/css/bootstrap.css');

class RESTraveler extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUrl: '/rest/api',
      currentObjects: [],
      errors: {}
    }
  }

  travel(location) {
    this.props.travelTo(location).then(
      response=> this.setState({currentObjects: JSON.parse(response.response.data.message)}),
      error => this.setState({errors: {request: error.response.data.message}}));
  }

  render() {
    return (
      <h1>This is a RESTraveler</h1>
    );
  }
}

RESTraveler.propTypes = {
  travelTo: PropTypes.func.isRequired
}

RESTraveler.contextTypes = {
  router: PropTypes.obj.isRequired
}

export default RESTraveler;