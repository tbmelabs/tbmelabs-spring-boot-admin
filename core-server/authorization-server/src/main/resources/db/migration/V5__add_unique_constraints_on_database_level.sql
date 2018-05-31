-----------------------------------
--- UNIQUE CONSTRAINT SECURITY  ---
-----------------------------------
ALTER TABLE clients
  ADD CONSTRAINT unique_client_id UNIQUE (client_id);

ALTER TABLE users
  ADD CONSTRAINT unique_username UNIQUE (username),
  ADD CONSTRAINT unique_email UNIQUE (email);

ALTER TABLE email_confirmation_tokens
  ADD CONSTRAINT unique_token_string UNIQUE (token_string);