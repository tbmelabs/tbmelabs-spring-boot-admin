'use strict';

import React, {Component} from 'react';

import {Route, Switch, HashRouter} from 'react-router-dom';

import {anonymousUsersOnly, authenticatedUsersOnly} from './utils/authUtils';

import Login from './container/Login';
import Select from './container/Select';

class Router extends Component {
    render() {
        return (
            <HashRouter>
                <Switch>
                    <Route exact path="/" component={anonymousUsersOnly(Login)}/>
                    <Route path="/select" component={authenticatedUsersOnly(Select)}/>
                </Switch>
            </HashRouter>
        );
    }
}