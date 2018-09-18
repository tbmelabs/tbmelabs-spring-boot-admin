-----------------------------------
--- DEFAULT CLIENT AUTHORITIES  ---
-----------------------------------
INSERT INTO client_authorities(created, last_updated, name)
  VALUES (now(), now(), 'MANAGE_SERVICES'),
    (now(), now(), 'UI_ENTRY_SERVER'),
    (now(), now(), 'ADMIN_SERVER');