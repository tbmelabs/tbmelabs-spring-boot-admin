'use strict';

import React from 'react';

import Overlay from 'react-bootstrap/lib/Overlay';
import Popover from 'react-bootstrap/lib/Popover';

class InputErrorOverlay extends React.Component {
    render() {
        return (
            <Overlay
                show={this.props.show}
                target={this.props.target}
                placement='right'
                container={this.props.container}
                containerPadding={20}
            >
                <Popover id='popover-container-{this.props.title}' title={this.props.title}>
                    {this.props.message}
                </Popover>
            </Overlay>);
    }
}

export default InputErrorOverlay;