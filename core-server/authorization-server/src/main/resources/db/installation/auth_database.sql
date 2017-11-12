-- user configuration
CREATE USER auth_database_user WITH PASSWORD 'auth_database_user_password';
GRANT CREATE, CONNECT ON DATABASE auth_server_database TO auth_database_user;

-- connect to created database
\connect auth_server_database

-----------------------------------
---			ACCOUNTS			---
-----------------------------------
CREATE TABLE users (
    id bigserial NOT NULL,
    created timestamp without time zone NOT NULL,
    last_updated timestamp without time zone NOT NULL,
    username character varying(64) NOT NULL,
    email character varying(128) NOT NULL,
    password character(60) NOT NULL,
    is_blocked boolean NOT NULL
);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

GRANT SELECT
	ON users TO auth_database_user;

-----------------------------------
---			PRIVILEGES			---
-----------------------------------
CREATE TABLE user_roles (
    id bigserial NOT NULL,
    created timestamp without time zone NOT NULL,
    last_updated timestamp without time zone NOT NULL,
    name character varying(16) NOT NULL
);

ALTER TABLE ONLY user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (id);

GRANT SELECT, INSERT
	ON user_roles TO auth_database_user;
	
-- add default roles
INSERT INTO user_roles(created, last_updated, name)
	VALUES	(now(), now(), 'USER'),
			(now(), now(), 'ADMIN');
	
REVOKE INSERT
	ON user_roles FROM auth_database_user;

-----------------------------------
---		  USER HAS ROLES		---
-----------------------------------
CREATE TABLE user_has_roles (
    created timestamp without time zone NOT NULL,
    last_updated timestamp without time zone NOT NULL,
    user_id bigint NOT NULL,
    user_role_id bigint NOT NULL
);

ALTER TABLE ONLY user_has_roles
    ADD CONSTRAINT user_has_roles_pkey PRIMARY KEY (user_id, user_role_id);

ALTER TABLE ONLY user_has_roles
    ADD CONSTRAINT user_has_roles_user FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY user_has_roles
    ADD CONSTRAINT user_has_roles_role FOREIGN KEY (user_role_id) REFERENCES user_roles(id);

GRANT SELECT
	ON user_has_roles TO auth_database_user;
	
-----------------------------------
---			  CLIENTS			---
-----------------------------------
CREATE TABLE clients (
	id bigserial NOT NULL,
	created timestamp without time zone NOT NULL,
	last_updated timestamp without time zone NOT NULL,
	name character varying(64) NOT NULL,
	secret character(36) NOT NULL,
	secret_required boolean NOT NULL,
	auto_approve boolean NOT NULL,
	access_token_validity integer NOT NULL,
	refresh_token_validity integer NOT NULL,
	redirect_uri character varying(256)
);

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);

GRANT SELECT
	ON clients TO auth_database_user;

-----------------------------------
---			AUTHORITIES			---
-----------------------------------
CREATE TABLE client_authorities (
    id bigserial NOT NULL,
    created timestamp without time zone NOT NULL,
    last_updated timestamp without time zone NOT NULL,
    name character varying(16) NOT NULL
);

ALTER TABLE ONLY client_authorities
    ADD CONSTRAINT client_authorities_pkey PRIMARY KEY (id);

GRANT SELECT
	ON client_authorities TO auth_database_user;
		
-----------------------------------
---	  CLIENT HAS AUTHORITIES  	---
-----------------------------------
CREATE TABLE client_has_authorities (
    created timestamp without time zone NOT NULL,
    last_updated timestamp without time zone NOT NULL,
    client_id bigint NOT NULL,
    client_authority_id bigint NOT NULL
);

ALTER TABLE ONLY client_has_authorities
    ADD CONSTRAINT client_has_authorities_pkey PRIMARY KEY (client_id, client_authority_id);

ALTER TABLE ONLY client_has_authorities
    ADD CONSTRAINT client_has_authorities_client FOREIGN KEY (client_id) REFERENCES clients(id);

ALTER TABLE ONLY client_has_authorities
    ADD CONSTRAINT client_has_authorities_authority FOREIGN KEY (client_authority_id) REFERENCES client_authorities(id);

GRANT SELECT
	ON client_has_authorities TO auth_database_user;
	
-----------------------------------
---			GRANT TYPES			---
-----------------------------------
CREATE TABLE client_grant_types (
	id bigserial NOT NULL,
	created timestamp without time zone NOT NULL,
	last_updated timestamp without time zone NOT NULL,
	name character varying(32) NOT NULL
);

ALTER TABLE ONLY client_grant_types
    ADD CONSTRAINT client_grant_types_pkey PRIMARY KEY (id);

-- Add default values
INSERT INTO client_grant_types (created, last_updated, name)
	VALUES (now(), now(), 'authorization_code'),
	(now(), now(), 'refresh_token'),
	(now(), now(), 'implicit'),
	(now(), now(), 'password');

GRANT SELECT
	ON client_grant_types TO auth_database_user;

-----------------------------------
---	  CLIENT HAS GRANT TYPES	---
-----------------------------------
CREATE TABLE client_has_grant_types (
    created timestamp without time zone NOT NULL,
    last_updated timestamp without time zone NOT NULL,
    client_id bigint NOT NULL,
    client_grant_type_id bigint NOT NULL
);

ALTER TABLE ONLY client_has_grant_types
    ADD CONSTRAINT client_has_grant_types_pkey PRIMARY KEY (client_id, client_grant_type_id);

ALTER TABLE ONLY client_has_grant_types
    ADD CONSTRAINT client_has_grant_types_client FOREIGN KEY (client_id) REFERENCES clients(id);

ALTER TABLE ONLY client_has_grant_types
    ADD CONSTRAINT client_has_grant_types_grant_type FOREIGN KEY (client_grant_type_id) REFERENCES client_grant_types(id);

GRANT SELECT
	ON client_has_grant_types TO auth_database_user;

-----------------------------------
---			  SCOPES			---
-----------------------------------
CREATE TABLE client_scopes (
	id bigserial NOT NULL,
	created timestamp without time zone NOT NULL,
	last_updated timestamp without time zone NOT NULL,
	name character varying(32) NOT NULL
);

ALTER TABLE ONLY client_scopes
    ADD CONSTRAINT client_scopes_pkey PRIMARY KEY (id);

GRANT SELECT
	ON client_scopes TO auth_database_user;

-----------------------------------
---		 CLIENT HAS SCOPES  	---
-----------------------------------
CREATE TABLE client_has_scopes (
    created timestamp without time zone NOT NULL,
    last_updated timestamp without time zone NOT NULL,
    client_id bigint NOT NULL,
    client_scope_id bigint NOT NULL
);

ALTER TABLE ONLY client_has_scopes
    ADD CONSTRAINT client_has_scopes_pkey PRIMARY KEY (client_id, client_scope_id);

ALTER TABLE ONLY client_has_scopes
    ADD CONSTRAINT client_has_scopes_client FOREIGN KEY (client_id) REFERENCES clients(id);

ALTER TABLE ONLY client_has_scopes
    ADD CONSTRAINT client_has_scopes_scope FOREIGN KEY (client_scope_id) REFERENCES client_scopes(id);

GRANT SELECT
	ON client_has_scopes TO auth_database_user;

-----------------------------------
---	  AUTHENTICATION LOGGING  	---
-----------------------------------
CREATE TABLE authentication_log (
	id bigserial NOT NULL,
	created timestamp without time zone NOT NULL,
	last_updated timestamp without time zone NOT NULL,
	state character(3) NOT NULL,
	ip character(45) NOT NULL,
	message character varying(256),
	user_id bigint NOT NULL
);

ALTER TABLE ONLY authentication_log
    ADD CONSTRAINT authentication_log_pkey PRIMARY KEY (id);

ALTER TABLE ONLY authentication_log
	ADD CONSTRAINT authentication_for_user FOREIGN KEY (user_id) REFERENCES users(id);

GRANT INSERT
	ON authentication_log TO auth_database_user;

-----------------------------------
---	  AUTHENTICATION LOGGING  	---
-----------------------------------
CREATE TABLE blacklisted_ips (
	id bigserial NOT NULL,
	created timestamp without time zone NOT NULL,
	last_updated timestamp without time zone NOT NULL,
	ip character(45) NOT NULL
);

ALTER TABLE ONLY blacklisted_ips
    ADD CONSTRAINT blacklisted_ips_pkey PRIMARY KEY (id);

GRANT INSERT
	ON blacklisted_ips TO auth_database_user;