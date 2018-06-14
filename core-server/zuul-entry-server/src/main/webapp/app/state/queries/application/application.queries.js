// @flow

import getStore from '../../../getStore';

import {launchApplicationAction} from '../../actions/application/application.actions';

export function launchApplication() {
  getStore().dispatch(launchApplicationAction());
}
