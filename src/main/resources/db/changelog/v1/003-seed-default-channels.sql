--liquibase formatted sql


--changeset veteroch4k:003-seed-default-channels context:!test
INSERT INTO channels (username, is_default) VALUES
                                                ('progjob',             TRUE),
                                                ('hiddengurus',         TRUE),
                                                ('young_intern',        TRUE),
                                                ('yuniorapp',           TRUE),
                                                ('young_june',          TRUE),
                                                ('juno_jobs',           TRUE),
                                                ('ycrjobs',             TRUE),
                                                ('back_rabota',         TRUE),
                                                ('javadevjob',          TRUE),
                                                ('juniors_rabota_jobs', TRUE),
                                                ('myresume_ru',         TRUE)
ON CONFLICT (username) DO NOTHING;

--rollback DELETE FROM channels WHERE is_default = TRUE;