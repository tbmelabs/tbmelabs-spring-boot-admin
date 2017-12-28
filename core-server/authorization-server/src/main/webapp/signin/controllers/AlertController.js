'use strict';

import {getUrlParameter} from '../utils/UrlUtils';

export function controlAlert() {
  if (getUrlParameter('error')) {
    $('#signin-failed-alert').show();
  }
}