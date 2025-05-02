CREATE USER course_system_full PASSWORD 'course_system_full';
CREATE USER course_system_app PASSWORD 'course_system_app';

CREATE SCHEMA course_system AUTHORIZATION wisest;

ALTER DEFAULT PRIVILEGES IN SCHEMA course_system GRANT SELECT ON TABLES TO course_system_app;
ALTER DEFAULT PRIVILEGES IN SCHEMA course_system GRANT INSERT ON TABLES TO course_system_app;
ALTER DEFAULT PRIVILEGES IN SCHEMA course_system GRANT UPDATE ON TABLES TO course_system_app;
ALTER DEFAULT PRIVILEGES IN SCHEMA course_system GRANT DELETE ON TABLES TO course_system_app;


GRANT USAGE, CREATE ON SCHEMA course_system TO course_system_full WITH GRANT OPTION;
GRANT USAGE ON SCHEMA course_system TO course_system_app;

ALTER DEFAULT PRIVILEGES IN SCHEMA course_system GRANT ALL PRIVILEGES ON TABLES TO course_system_full;
ALTER DEFAULT PRIVILEGES IN SCHEMA course_system GRANT ALL PRIVILEGES ON SEQUENCES TO course_system_full;
