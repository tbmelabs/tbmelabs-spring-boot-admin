'use strict';

import React from 'react';
import {render} from 'react-dom';

import {createStore, applyMiddleware, compose} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import reducers from './reducers';

import {CookiesProvider} from 'react-cookie';

import {BrowserRouter} from 'react-router-dom';
import {Route, Switch} from 'react-router-dom';

import Login from './container/Login';

const store = createStore(
    reducers,
    compose(
        applyMiddleware(thunk),
        window.devToolsExtension ? window.devToolsExtension() : f => f
    )
);

require('./styles/tbme-tv.css');

class App extends React.Component {
    render() {
        return (
            <Provider store={store}>
                <CookiesProvider>
                    <BrowserRouter>
                        <Switch>
                            <Route path="/" component={Login}/>
                        </Switch>
                    </BrowserRouter>
                </CookiesProvider>
            </Provider>
        );
    }
}

render(<App/>, document.getElementById('app'));

document.getElementById('enable-script-div').remove();