'use strict';

import axios from 'axios';

import {SET_CURRENT_USER} from './types';

export function setCurrentUser(user) {
    return {
        type: SET_CURRENT_USER,
        user
    };
}

export function authenticateUser(data) {
    var formData = new FormData();
    formData.append('username', data.username);
    formData.append('password', data.password);

    return dispatch => {
        return axios.post('/login', formData)
            .then(response => {
                console.log(response);

                /*
                 * TODO: Apply bearer token to axios
                 */
            });
    }
}