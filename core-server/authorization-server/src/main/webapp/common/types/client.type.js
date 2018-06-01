// @flow

import {type scopeType} from './scope.type';
import {type authorityType} from './authority.type';
import {type grantTypeType} from './grantType.type';

export type clientType = {
  id?: number;
  created?: number;
  lastUpdated?: number;
  clientId: string;
  secret: string;
  isSecretRequired: boolean;
  isAutoApprove: boolean;
  accessTokenValiditySeconds: number;
  refreshTokenValiditySeconds: number;
  redirectUris: string[];
  grantTypes: grantTypeType[];
  grantedAuthorities: authorityType[];
  scopes: scopeType[];
}
