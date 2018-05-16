// @flow

import getStore from '../../../getStore';

import {requestProfileAction} from '../../actions/profile';

export function requestProfile() {
  getStore().dispatch(requestProfileAction());
}
