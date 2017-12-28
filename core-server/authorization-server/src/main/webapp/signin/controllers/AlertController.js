'use strict';

import {getUrlParameter} from '../utils/UrlUtils';

function registerErrorCloseListener() {
  $('#signin-failed-alert-dismiss').click(() => {
    $('#signin-failed-alert').addClass('fade');

    setTimeout(() => {
      $('#signin-failed-alert').hide();
    }, 500);
  });
}

function registerLogoutCloseListener() {
  $('#logout-succeed-alert-dismiss').click(() => {
    $('#logout-succeed-alert').addClass('fade');

    setTimeout(() => {
      $('#logout-succeed-alert').hide();
    }, 500);
  });
}

export function controlAlerts() {
  if (getUrlParameter('error')) {
    registerErrorCloseListener();
    $('#signin-failed-alert').show();
  }

  if (getUrlParameter('logout')) {
    registerLogoutCloseListener();
    $('#logout-succeed-alert').show();
  }
}