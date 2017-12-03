'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import queryString from 'query-string';

import {authenticateUser} from '../../actions/authActions';

import Jumbotron from 'react-bootstrap/lib/Jumbotron';

import UsernamePasswordLoginForm from '../../components/signin/UsernamePasswordLoginForm';

require('bootstrap/dist/css/bootstrap.css');
require('../../styles/signin.css');

class Signin extends Component {
  componentDidMount() {
    document.title = this.props.texts.tab_header;
  }

  render() {
    const {redirect} = queryString.parse(this.context.router.route.location.search);
    const {texts} = this.props;
    const {authenticateUser} = this.props.actions;

    return (
      <div>
        <Jumbotron>
          <h1>{texts.jumbotron_title}</h1>
          <p>{texts.jumbotron_subtitle}</p>
        </Jumbotron>

        <div className='signin-form'>
          <UsernamePasswordLoginForm authenticateUser={authenticateUser} redirectUrl={redirect} texts={texts}/>
        </div>
      </div>
    );
  }
}

Signin.propTypes = {
  texts: PropTypes.object.isRequired,
  actions: PropTypes.object.isRequired
}

Signin.contextTypes = {
  router: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.signin
  }
}


function mapDispatchToProps(dispatch) {
  return {
    actions: {
      authenticateUser: bindActionCreators(authenticateUser, dispatch)
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Signin);