INSERT INTO local_user (username, email, password, first_name, last_name, created_at)
VALUES (
    'system_admin',
    'system.admin@gmail.com',
    '$2a$10$btvcrRRGRexITfZ1xKDFx.NtpLgCfnV5c3fLxtDIO8FooJIEt.Kdq',
    'System',
    'Admin',
    NOW()
);

