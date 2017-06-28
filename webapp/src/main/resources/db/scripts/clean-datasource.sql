-- NOTE: You should not use this script in productive environments:
-- It deletes ALL your stored data without any backup!
delete from account;

delete from access_level;
delete from account_registration_token;

delete from password_reset_token;

delete from authentication_log;