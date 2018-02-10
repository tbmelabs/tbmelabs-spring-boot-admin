'use strict';

import React, {Component} from 'react';
import PropTypes from 'prop-types';

import Navbar from 'react-bootstrap/lib/Navbar';
import Nav from 'react-bootstrap/lib/Nav';
import NavItem from 'react-bootstrap/lib/NavItem';
import NavDropdown from 'react-bootstrap/lib/NavDropdown';
import MenuItem from 'react-bootstrap/lib/MenuItem';

require('bootstrap/dist/css/bootstrap.css');

class Navigation extends Component {
  constructor(props) {
    super(props);

    this.LOGIN_EVENT = 'LOGIN';
    this.LOGOUT_EVENT = 'LOGOUT';

    this.onClick = this.onClick.bind(this);
  }

  onClick(event) {
    const {login, logout} = this.props;

    switch (event.target.name) {
      case this.LOGIN_EVENT:
        login();
        break;
      case this.LOGOUT_EVENT:
        logout();
        break;
    }
  }

  render() {
    const {isAuthenticated, texts} = this.props;

    return (
      <Navbar collapseOnSelect>
        <Navbar.Header>
          <Navbar.Brand>
            <a href="#">TBME TV</a>
          </Navbar.Brand>
          <Navbar.Toggle/>
        </Navbar.Header>
        <Navbar.Collapse>
          <Nav>
            {/*<NavItem eventKey={1} href="#">Link</NavItem>*/}
            {/*<NavItem eventKey={2} href="#">Link</NavItem>*/}
            {/*<NavDropdown eventKey={3} title="Dropdown" id="basic-nav-dropdown">*/}
            {/*<MenuItem eventKey={3.1}>Action</MenuItem>*/}
            {/*<MenuItem eventKey={3.2}>Another action</MenuItem>*/}
            {/*<MenuItem eventKey={3.3}>Something else here</MenuItem>*/}
            {/*<MenuItem divider/>*/}
            {/*<MenuItem eventKey={3.3}>Separated link</MenuItem>*/}
            {/*</NavDropdown>*/}
          </Nav>
          <Nav pullRight>
            {
              !isAuthenticated ?
                <NavItem name={this.LOGIN_EVENT} onClick={this.onClick}>{texts.login}</NavItem>
                :
                <NavItem name={this.LOGOUT_EVENT} onClick={this.onClick}>{texts.logout}</NavItem>
            }
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  }
}

Navigation.propTypes = {
  login: PropTypes.func.isRequired,
  logout: PropTypes.func.isRequired,
  isAuthenticated: PropTypes.bool.isRequired,
  texts: PropTypes.object.isRequired
}

export default Navigation;