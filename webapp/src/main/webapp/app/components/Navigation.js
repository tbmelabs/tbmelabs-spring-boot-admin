'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';
import {LinkContainer} from 'react-router-bootstrap';

import {
  ROLE_GUEST,
  ROLE_USER,
  ROLE_CONTENT_ADMIN,
  ROLE_SERVER_ADMIN,
  ROLE_APPLICATION_ADMIN
} from '../utils/security/roles';

import Navbar from 'react-bootstrap/lib/Navbar';
import Nav from 'react-bootstrap/lib/Nav';
import NavItem from 'react-bootstrap/lib/NavItem';
import NavDropdown from 'react-bootstrap/lib/NavDropdown';
import MenuItem from 'react-bootstrap/lib/MenuItem';

require('bootstrap/dist/css/bootstrap.css');

class Navigation extends React.Component {
  constructor(props) {
    super(props);

    this.handleNavigationSelect = this.handleNavigationSelect.bind(this);
    this.handleLogout = this.handleLogout.bind(this);
    this.logout = this.logout.bind(this);
  }

  handleNavigationSelect(eventKey, event) {
    event.preventDefault();

    switch (eventKey) {
      case 'restraveler':
        this.context.router.history.push('/admin/restraveler');
    }
  }

  handleLogout(eventKey, event) {
    event.preventDefault();

    if (eventKey === 'logout') {
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
      <div>
        <Nav>
        </Nav>
        <Nav pullRight>
          <LinkContainer to='/login'>
            <NavItem>Login</NavItem>
          </LinkContainer>
          <LinkContainer to='/register'>
            <NavItem>Sign Up</NavItem>
          </LinkContainer>
        </Nav>
      </div>
    );

    const authenticatedNav = (
      <div>
        <Nav onSelect={this.handleNavigationSelect}>
          {this.props.isAuthenticated && this.props.user.accessLevel.id >= ROLE_CONTENT_ADMIN ?
            <NavDropdown title='Administration' id='nav-admin-dropdwon'>
              <MenuItem eventKey='restraveler'>RESTraveler</MenuItem>
            </NavDropdown> : null}
        </Nav>
        <Nav onSelect={this.handleLogout} pullRight>
          <LinkContainer to='#'>
            <NavItem eventKey='logout'>Logout</NavItem>
          </LinkContainer>
        </Nav>
      </div>
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
            {this.props.isAuthenticated ? authenticatedNav : guestNav }
          </Navbar.Collapse>
        </Navbar>
      </header>
    );
  }
}

Navigation.propTypes = {
  isAuthenticated: PropTypes.bool.isRequired,
  user: PropTypes.object.isRequired,
  logout: PropTypes.func.isRequired
}

Navigation.contextTypes = {
  router: PropTypes.object.isRequired
}

export default Navigation;