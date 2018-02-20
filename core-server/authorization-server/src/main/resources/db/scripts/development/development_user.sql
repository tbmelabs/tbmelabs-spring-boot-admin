-- user configuration
CREATE USER auth_database_user WITH PASSWORD 'auth_database_user_password';
GRANT CREATE, CONNECT ON DATABASE auth_server_database TO auth_database_user;