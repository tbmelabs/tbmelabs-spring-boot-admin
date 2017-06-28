'use strict';

import React from 'react';

import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import {Link} from 'react-router-dom';
import {LinkContainer} from 'react-router-bootstrap';

import {logout} from '../actions/authActions';

import Navbar from 'react-bootstrap/lib/Navbar';
import Nav from 'react-bootstrap/lib/Nav';
import NavItem from 'react-bootstrap/lib/NavItem';

require('bootstrap/dist/css/bootstrap.css');

class Navigation extends React.Component {
  logout(event) {
    event.preventDefault();
    this.props.logout();
  }

  render() {
    const {isAuthenticated} = this.props.auth;

    return (
      <header>
        <Navbar>
          <Navbar.Header>
            <Navbar.Brand>
              <Link to="/">TBME Labs TV</Link>
            </Navbar.Brand>
          </Navbar.Header>
          <Nav>
            {!isAuthenticated && <LinkContainer to='/login'><NavItem>Login</NavItem></LinkContainer>}
            {!isAuthenticated && <LinkContainer to='/register'><NavItem>Register</NavItem></LinkContainer>}
            {isAuthenticated && <LinkContainer to='/home'><NavItem>Home</NavItem></LinkContainer>}
          </Nav>
        </Navbar>
      </header>
    );
  }
}

Navigation.propTypes = {
  auth: PropTypes.object.isRequired,
  logout: PropTypes.func.isRequired
}

function mapStateToProps(state) {
  return {
    auth: state.auth
  };
}

export default connect(mapStateToProps, {logout})(Navigation);