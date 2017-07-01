'use strict';

import React from "react";

import Alert from "react-bootstrap/lib/Alert";

require('bootstrap/dist/css/bootstrap.css');

class CustomAlert extends React.Component {
    render() {
        return (
            <Alert bsStyle={this.props.type} className={this.props.className}>
                <strong>{this.props.title}</strong> {this.props.message}
            </Alert>);
    }
}

export default CustomAlert;