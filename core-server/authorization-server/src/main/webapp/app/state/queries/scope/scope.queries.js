// @flow

import getStore from '../../../getStore';

import {requestClientScopesAction} from '../../actions/scope';

export function requestClientScopes() {
  getStore().dispatch(requestClientScopesAction());
}
