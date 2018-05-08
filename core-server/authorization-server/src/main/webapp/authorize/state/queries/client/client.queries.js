// @flow

import getStore from '../../../getStore';

import {requestClientScopesAction} from '../../actions/client';

export function requestClientScopes(clientId: string) {
  getStore().dispatch(requestClientScopesAction(clientId));
}
