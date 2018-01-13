'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {logout} from '../../actions/authActions';

import Navigation from '../../components/Navigation';

class Navbar extends Component {
  render() {
    const {texts} = this.props;

    return (
      <div>
        <Navigation logout={logout} texts={texts}/>
      </div>
    );
  }
}

Navbar.propTypes = {
  texts: PropTypes.object.isRequired
}

function mapStateToProps(state) {
  return {
    texts: state.language.texts.navbar
  }
}

export default connect(mapStateToProps, null)(Navbar);