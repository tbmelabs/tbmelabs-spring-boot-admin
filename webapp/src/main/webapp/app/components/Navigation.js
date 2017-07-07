'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';
import {LinkContainer} from 'react-router-bootstrap';

import Navbar from 'react-bootstrap/lib/Navbar';
import Nav from 'react-bootstrap/lib/Nav';
import NavItem from 'react-bootstrap/lib/NavItem';

require('bootstrap/dist/css/bootstrap.css');

class Navigation extends React.Component {
  constructor(props) {
    super(props);

    this.catchLogout = this.catchLogout.bind(this);
    this.logout = this.logout.bind(this);
  }

  catchLogout(eventKey, event) {
    if (eventKey === 'logout') {
      event.preventDefault();

      this.logout();
    }
  }

  logout() {
    this.props.logout().then(
      response => {
        if (this.context.router.history.location !== '/') {
          this.context.router.history.push('/');
        }
      }
    )
  }

  render() {
    const guestNav = (
      <Nav>
        <LinkContainer to='/login'><NavItem>Login</NavItem></LinkContainer>
        <LinkContainer to='/register'><NavItem>Register</NavItem></LinkContainer>
      </Nav>
    );

    const authenticatedNav = (
      <Nav onSelect={this.catchLogout}>
        <LinkContainer to='#'><NavItem eventKey='logout'>Logout</NavItem></LinkContainer>
      </Nav>
    );

    return (
      <header>
        <Navbar collapseOnSelect>
          <Navbar.Header>
            <Navbar.Brand>
              <Link to="/">TBME Labs TV</Link>
            </Navbar.Brand>
            <Navbar.Toggle/>
          </Navbar.Header>
          <Navbar.Collapse>
            {this.props.isAuthenticated ? authenticatedNav : guestNav}
          </Navbar.Collapse>
        </Navbar>
      </header>
    );
  }
}

Navigation.propTypes = {
  isAuthenticated: PropTypes.bool.isRequired,
  logout: PropTypes.func.isRequired
}

Navigation.contextTypes = {
  router: PropTypes.object.isRequired
}

export default Navigation;