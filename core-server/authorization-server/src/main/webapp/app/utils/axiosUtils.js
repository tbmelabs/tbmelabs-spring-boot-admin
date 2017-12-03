'use strict';

import axios from 'axios';

export default axios.create({
  auth: {
    username: require('../config/config.json').clientId
  }
});