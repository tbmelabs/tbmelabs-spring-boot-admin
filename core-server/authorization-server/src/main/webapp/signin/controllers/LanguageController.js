'use strict';

const texts = require('../config/languages/en.json').signin;

export function nameViewComponents() {
  $('#jumbotron_title').text(texts.jumbotron_title);
  $('#jumbotron_text').text(texts.jumbotron_text);
  $('#signin_failed_alert_title').text(texts.signin_failed_alert_title);
  $('#signin_failed_alert_text').text(texts.signin_failed_alert_text);
  $('#username_form_control').text(texts.username_form_control);
  $('#password_form_control').text(texts.password_form_control);
  $('#signup_link_text').text(texts.signup_link_text);
  $('#signin_button_text').text(texts.signin_button_text);
}