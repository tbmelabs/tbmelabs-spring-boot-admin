-----------------------------------
--   BLACKLIST TO STORE RANGE   ---
-----------------------------------
ALTER TABLE blacklisted_ips
  RENAME COLUMN ip TO start_ip;

ALTER TABLE blacklisted_ips
  ADD COLUMN end_ip character varying(45) NOT NULL;