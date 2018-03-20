// @flow
'use strict';

import profileType from '../types/profileType';

import AuthorityConstants from '../contants/AuthorityConstants';
import roleType from '../types/roleType';

export default function hasAuthority(authority: string, profile: profileType) {
  if (profile == null || profile.roles == null || AuthorityConstants.indexOf(authority) === -1) {
    return false;
  }

  const applyingAuthorities = AuthorityConstants.splice(0, AuthorityConstants.indexOf(authority) + 1);

  return profile.roles.some((role: roleType) => applyingAuthorities.indexOf(role.name) !== -1);
}