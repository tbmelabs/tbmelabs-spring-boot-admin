// @flow

import getStore from '../../../getStore';

import {requestAuthenticationAction} from '../../actions/authentication';

export function requestAuthentication() {
  getStore().dispatch(requestAuthenticationAction());
}
