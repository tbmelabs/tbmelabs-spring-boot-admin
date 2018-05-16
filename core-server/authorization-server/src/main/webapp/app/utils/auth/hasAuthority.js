// @flow
'use strict';

import AuthorityConstants from '../../../common/contants/AuthorityConstants';

import {type profileType} from '../../../common/types/profile.type';
import {type roleType} from '../../../common/types/role.type';

export default (authority: string, profile: profileType) => {
  if (profile == null || profile.roles == null || AuthorityConstants.indexOf(
          authority) === -1) {
    return false;
  }

  const applyingAuthorities = AuthorityConstants.slice(
      0, AuthorityConstants.indexOf(authority) + 1);

  return profile.roles.some(
      (role: roleType) => applyingAuthorities.indexOf(role.name) !== -1);
}