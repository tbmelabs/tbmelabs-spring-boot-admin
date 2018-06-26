// @flow

import {type roleType} from './role.type';

export type profileType = {
  id?: number;
  created?: number;
  lastUpdated?: number;
  username: string;
  email: string;
  isEnabled: boolean;
  isBlocked: boolean;
  roles: roleType[];
}
