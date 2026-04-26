-- Insert admin user (PostgreSQL syntax)
INSERT INTO sys_user (username, password, role)
VALUES ('24RP05647', '24RP15903', 'ADMIN')
ON CONFLICT (username) DO NOTHING;
