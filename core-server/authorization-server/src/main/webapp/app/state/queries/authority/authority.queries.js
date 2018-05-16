// @flow

import getStore from '../../../getStore';

import {requestClientAuthoritiesAction} from '../../actions/authority';

export function requestClientAuthorities() {
  getStore().dispatch(requestClientAuthoritiesAction());
}
