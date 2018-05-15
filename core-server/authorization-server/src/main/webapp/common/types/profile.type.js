// @flow

import type roleType from './role.type';

type profileType = {
  id?: number;
  created?: number;
  lastUpdated?: number;
  username: string;
  email: string;
  isEnabled: boolean;
  isBlocked: boolean;
  roles: roleType[];
}

export default profileType;
