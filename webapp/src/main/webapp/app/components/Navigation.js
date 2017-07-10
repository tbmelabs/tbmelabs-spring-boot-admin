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

    this.handleLogout = this.handleLogout.bind(this);
    this.logout = this.logout.bind(this);
  }

  handleLogout(eventKey, event) {
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
        <Nav>
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
  logout: PropTypes.func.isRequired
}

Navigation.contextTypes = {
  router: PropTypes.object.isRequired
}

export default Navigation;