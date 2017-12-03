'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {loadAvailableApplications} from '../../utils/selectUtils';

import Panel from 'react-bootstrap/lib/Panel';

import Option from '../../components/select/Option';
import CollapsableAlert from '../../components/common/CollapsableAlert';

require('bootstrap/dist/css/bootstrap.css');
require('../../styles/select.css');

class Select extends Component {
  constructor(props) {
    super(props);

    this.state = {
      applications: [],
      errors: {}
    }
  }

  componentWillMount() {
    loadAvailableApplications().then(
      response => {
        const {applications} = response.data;
        this.setState({applications: applications});
      }, error => {
        this.setState({applications: {}, errors: {form: error.response.data.message}});
      }
    )
  }

  render() {
    const {user} = this.props;
    const {applications} = this.state;

    return (
      <div>
        <h1>Welcome to TBME-Labs!</h1>
        <h3 className='text-muted'>Choose your application:</h3>

        <hr/>

        <CollapsableAlert style='danger' title='Unable not load applications: '
                          message={this.state.errors.form}
                          collapse={!!this.state.errors.form}/>

        <Panel className='option-holder'>
          {
            applications.map((element, iterator) => {
              return (
                <Option key={'option-' + iterator} name={element.name} link={element.url} user={user}/>
              );
            })
          }
        </Panel>
      </div>
    );
  }
}

Select.propTypes = {
  user: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    user: state.auth.user
  };
}

export default connect(mapStateToProps, null)(Select);