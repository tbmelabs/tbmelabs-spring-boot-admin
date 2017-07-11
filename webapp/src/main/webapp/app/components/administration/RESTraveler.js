'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import Button from 'react-bootstrap/lib/Button';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';

require('bootstrap/dist/css/bootstrap.css');

class RESTraveler extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUrl: '/rest/api',
      limit: 0,
      history: [],
      errors: {}
    }

    this.onChange = this.onChange.bind(this);
    this.onBack = this.onBack.bind(this);
    this.travel = this.travel.bind(this);
  }

  onChange(event) {
    this.setState({[event.target.name]: event.target.value});
  }

  onBack(event) {
    const history = this.state.history;
    const lastLocation = history.pop();

    this.setState({history: history});
    this.travel(lastLocation, true);
  }

  travel(location, isStepBack) {
    const history = this.state.history;

    this.props.travelTo(location).then(
      response=> {
        if (!isStepBack) {
          history.push(location);
        }

        this.setState({errors: {}, history: history});
      },
      error => this.setState({errors: {request: error.response.data.message}})
    );
  }

  render() {
    return (
      <Grid>
        <Row>
          <Col xs={18}>
            <CollapsableAlert collapse={!!this.state.errors.form} style='danger' title='Login failed: '
                              message={this.state.errors.form}/>
          </Col>
        </Row>
        <Row>
          <Col xs={12}>
            <Button>Back</Button>
          </Col>
          <Col xs={6}>
            <ControlLabel>Limit:</ControlLabel>
            <FormControl name='limit' type='text' value={this.state.limit} onChange={this.onChange}/>
          </Col>
        </Row>
      </Grid>
    );
  }
}

RESTraveler.propTypes = {
  travelTo: PropTypes.func.isRequired
}

RESTraveler.contextTypes = {
  router: PropTypes.object.isRequired
}

export default RESTraveler;