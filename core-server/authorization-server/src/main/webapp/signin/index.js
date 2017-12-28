'use strict';

import {nameViewComponents} from './controllers/LanguageController';
import {controlAlert} from "./controllers/AlertController";

require('bootstrap/dist/css/bootstrap.css');

/**
 * TBME Labs - Sign In
 * -------------------
 * Improves the user experience by adding language specific texts, controlling alerts and cookies.
 */
$(document).ready(() => {
  nameViewComponents();
  controlAlert();
});