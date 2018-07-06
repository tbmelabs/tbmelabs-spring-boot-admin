-----------------------------------
---    UPDATE USERNAME FORMAT   ---
-----------------------------------
ALTER TABLE ONLY users
  ALTER COLUMN username TYPE character varying(20);
