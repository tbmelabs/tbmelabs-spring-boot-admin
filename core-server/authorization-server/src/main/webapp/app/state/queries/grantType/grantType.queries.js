// @flow

import getStore from '../../../getStore';

import {requestClientGrantTypesAction} from '../../actions/grantType';

export function requestClientGrantTypes() {
  getStore().dispatch(requestClientGrantTypesAction());
}
