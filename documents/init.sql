-- CREATE USER quarkustest WITH PASSWORD 'sqlquarkustest';
-- CREATE DATABASE quarkustest OWNER quarkustest;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles  -- SELECT list can be empty for this
      WHERE  rolname = 'quarkustest') THEN

      CREATE USER quarkustest WITH PASSWORD 'sqlquarkustest';
   END IF;
END
$do$;

SELECT 'CREATE DATABASE quarkustest OWNER quarkustest'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'quarkustest')\gexec
