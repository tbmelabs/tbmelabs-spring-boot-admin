'use strict';

import React from 'react';
import PropTypes from 'prop-types';

import {connect} from 'react-redux';

import axios from 'axios';

export function setAuthorizationToken(token) {
  if (token) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  } else {
    delete axios.defaults.headers.common['Authorization'];
  }
}