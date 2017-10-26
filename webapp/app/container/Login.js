'use strict';

import React, {Component} from 'react';

import {bindActionCreators} from 'redux';
import {connect} from 'react-redux';

import {authenticateUser} from '../actions/authActions';

import UsernamePasswordLogin from '../components/login/UsernamePasswordLogin';

require('bootstrap/dist/css/bootstrap.css');
require('../styles/login.css');

class Login extends Component {
    componentDidMount() {
        const progressBar = document.getElementById('ipl-progress-indicator')
        if (progressBar) {
            setTimeout(() => {
                progressBar.classList.add('available')
                setTimeout(() => {
                    progressBar.outerHTML = ''
                }, 2000)
            }, 1000)
        }
    }

    render() {
        const {authenticateUser} = this.props.actions;

        return (
            <div className='container'>
                <UsernamePasswordLogin authenticateUser={authenticateUser}/>
            </div>
        );
    }
}

function mapDispatchToProps(dispatch) {
    return {
        actions: {
            authenticateUser: bindActionCreators(authenticateUser, dispatch)
        }
    }
}

export default connect(null, mapDispatchToProps)(Login);