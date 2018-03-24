// @flow
'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import shortid from 'shortid';

import FormGroup from 'react-bootstrap/lib/FormGroup'
import Col from 'react-bootstrap/lib/Col';
import ControlLabel from 'react-bootstrap/lib/ControlLabel';
import FormControl from 'react-bootstrap/lib/FormControl';
import HelpBlock from 'react-bootstrap/lib/HelpBlock';
import Button from 'react-bootstrap/lib/Button';

require('bootstrap/dist/css/bootstrap.css');

const SPLITTERATOR = '-';

class InfiniteInputWrapper extends Component<InfiniteInputWrapper.propTypes> {
  onChange: () => void;
  addRedirectUri: () => void;

  constructor(props) {
    super(props);

    this.state = {
      values: ['']
    }

    this.onChange = this.onChange.bind(this);
    this.addNewEmptyValue = this.addNewEmptyValue.bind(this);
  }

  onChange(event: SyntheticInputEvent<HTMLInputElement>) {
    const {values} = this.state;
    const index = event.target.name.split(SPLITTERATOR)[1];

    values[index] = event.target.value;

    this.setState({values: values}, () => this.props.setConcatenatedValue(this.props.concatenateTextValues(this.state.values)));
  }

  addNewEmptyValue() {
    const {values} = this.state;
    values.push('');

    this.setState({values: values});
  }

  render() {
    const {controlId, validationState, inputName} = this.props;
    const shortId = shortid.generate();
    const continuousButtonRowOffset = this.state.values.length > 1 ? 'continuous-input-offset-top' : '';

    return (
      <FormGroup controlId={controlId} validationState={!!validationState ? 'error' : null}>
        <HelpBlock>{validationState}</HelpBlock>
        <Col componentClass={ControlLabel} sm={4}>{inputName}</Col>

        {this.state.values.map((value, index) => {
          const id = shortId + SPLITTERATOR + index;

          if (index === 0) {
            return (
              <Col key={id} sm={4}>
                <FormControl name={id} type='text' value={value}
                             onChange={this.onChange} required/>
                <FormControl.Feedback/>
              </Col>
            );
          }

          return (
            <Col key={id} smOffset={4} sm={4} className='continuous-input-offset-top'>
              <FormControl name={id} type='text' value={value}
                           onChange={this.onChange} required/>
              <FormControl.Feedback/>
            </Col>
          );
        })}

        <Col sm={2} className={continuousButtonRowOffset}>
          <Button bsStyle='primary' className='pull-right' onClick={this.addNewEmptyValue}>+</Button>
        </Col>
      </FormGroup>
    );
  }
}

InfiniteInputWrapper.propTypes = {
  controlId: PropTypes.string.isRequired,
  validationState: PropTypes.string.isRequired,
  inputName: PropTypes.string.isRequired,
  concatenateTextValues: PropTypes.func.isRequired,
  setConcatenatedValue: PropTypes.func.isRequired
}

export default InfiniteInputWrapper;