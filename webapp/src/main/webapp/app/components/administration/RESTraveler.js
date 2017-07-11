'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import CollapsableAlert from '../common/alert/CollapsableAlert';

import Grid from 'react-bootstrap/lib/Grid';
import Row from 'react-bootstrap/lib/Row';
import Col from 'react-bootstrap/lib/Col';
import Button from 'react-bootstrap/lib/Button';
import FormGroup from 'react-bootstrap/lib/FormGroup';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';

require('bootstrap/dist/css/bootstrap.css');

class RESTraveler extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUrl: '/rest/api',
      data: [],
      filter: 'id',
      links: [],
      limit: 0,
      history: [],
      errors: {}
    }

    this.onChange = this.onChange.bind(this);
    this.onBack = this.onBack.bind(this);
    this.travel = this.travel.bind(this);
  }

  componentWillMount() {
    this.travel(this.state.currentUrl, false);
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
      response => {
        const links = response.data._links;

        if (!isStepBack) {
          history.push(location);
        }

        // data:, links:,
        this.setState({filter: 'id', history: history, links: links, errors: {}});
      },
      error => this.setState({errors: {request: error.response.data.message}})
    );
  }

  render() {
    return (
      <Grid>
        <Row>
          <Col sm={6}>
            <Button>Back</Button>
          </Col>
          <FormGroup>
            <Col sm={2}>
              <FormControl componentClass='select' placeholder='Travel to..'>
                
              </FormControl>
            </Col>
          </FormGroup>
          <FormGroup>
            <Col sm={1}>
              <ControlLabel>Limit:</ControlLabel>
            </Col>
            <Col sm={1}>
              <FormControl name='limit' type='text' value={this.state.limit} onChange={this.onChange}/>
            </Col>
          </FormGroup>
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