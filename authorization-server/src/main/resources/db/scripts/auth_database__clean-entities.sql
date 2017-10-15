-- select database
\connect auth_server_database

-- delete * from tables
DELETE FROM client_has_authorities;
DELETE FROM client_has_grant_types;
DELETE FROM client_has_scopes;

DELETE FROM client_authorities;
DELETE FROM client_grant_types;
DELETE FROM client_scopes;

DELETE FROM clients;

DELETE FROM user_has_roles;

DELETE FROM user_roles;

DELETE FROM users;