'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {Route, Switch, Redirect} from 'react-router-dom';

import {bindActionCreators}from 'redux';
import {connect} from 'react-redux';

import {travelTo} from '../../actions/RESTravelerActions';

import RESTraveler from '../../components/administration/RESTraveler';

class Administration extends React.Component {
  render() {
    const {travelTo} = this.props.actions;

    return (
      <Switch>
        <Route path='/administration/restraveler'>
          <RESTraveler travelTo={travelTo}/>
        </Route>
      </Switch>
    );
  }
}

Administration.propTypes = {
  actions: PropTypes.obj.isRequired
}

Administration.contextTypes = {
  router: PropTypes.obj.isRequired
}

function mapDispatchToProps(dispatch) {
  return {
    actions: {
      travelTo: bindActionCreators(travelTo, dispatch)
    }
  }
}

export default connect(null, mapDispatchToProps)(Administration);