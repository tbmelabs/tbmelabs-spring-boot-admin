-----------------------------------
---    ADD ON DELETE CASCADE    ---
-----------------------------------
ALTER TABLE ONLY user_has_roles
  DROP CONSTRAINT user_has_roles_user,
  DROP CONSTRAINT user_has_roles_role,
  ADD CONSTRAINT user_has_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  ADD CONSTRAINT user_has_roles_role FOREIGN KEY (user_role_id) REFERENCES user_roles(id) ON DELETE RESTRICT;

ALTER TABLE ONLY client_has_authorities
  DROP CONSTRAINT client_has_authorities_client,
  DROP CONSTRAINT client_has_authorities_authority,
  ADD CONSTRAINT client_has_authorities_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
  ADD CONSTRAINT client_has_authorities_authority FOREIGN KEY (client_authority_id) REFERENCES client_authorities(id) ON DELETE RESTRICT;

ALTER TABLE ONLY client_has_grant_types
  DROP CONSTRAINT client_has_grant_types_client,
  DROP CONSTRAINT client_has_grant_types_grant_type,
  ADD CONSTRAINT client_has_grant_types_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
  ADD CONSTRAINT client_has_grant_types_grant_type FOREIGN KEY (client_grant_type_id) REFERENCES client_grant_types(id) ON DELETE RESTRICT;

ALTER TABLE ONLY client_has_scopes
  DROP CONSTRAINT client_has_scopes_client,
  DROP CONSTRAINT client_has_scopes_scope,
  ADD CONSTRAINT client_has_scopes_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
  ADD CONSTRAINT client_has_scopes_scope FOREIGN KEY (client_scope_id) REFERENCES client_scopes(id) ON DELETE RESTRICT;

ALTER TABLE ONLY authentication_log
  DROP CONSTRAINT authentication_log_user,
  ADD CONSTRAINT authentication_log_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE ONLY email_confirmation_tokens
  DROP CONSTRAINT email_confirmation_tokens_user,
  ADD CONSTRAINT email_confirmation_tokens_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
