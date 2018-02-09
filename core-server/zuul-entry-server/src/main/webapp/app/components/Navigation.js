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

    this.LOGOUT_EVENT = 'LOGOUT';

    this.onClick = this.onClick.bind(this);
  }

  onClick(event) {
    switch (event.target.name) {
      case this.LOGOUT_EVENT:
        this.props.logout().then(
          response => {
            window.location.replace(response.headers['no-redirect']);

            // console.log(response.headers['no-redirect']);
          }, error => {
            // TODO: Visualize error to user
            console.log(error);
          }
        );
        break;
    }
  }

  render() {
    const {texts} = this.props;

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
            <NavItem name={this.LOGOUT_EVENT} onClick={this.onClick}>{texts.logout}</NavItem>
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  }
}

Navigation.propTypes = {
  logout: PropTypes.func.isRequired,
  texts: PropTypes.object.isRequired
}

export default Navigation;