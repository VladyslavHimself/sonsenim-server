CREATE TABLE card (
        interval_strength FLOAT4,
        created_date TIMESTAMP(6),
        deck_id BIGINT NOT NULL,
        id BIGSERIAL NOT NULL,
        last_modified_date TIMESTAMP(6),
        next_repetition_time TIMESTAMP(6),
        definition VARCHAR(255) NOT NULL,
        explanation VARCHAR(255),
        primary_word VARCHAR(255) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE deck (
        is_flashcard_normal BOOLEAN NOT NULL,
        is_flashcard_reversed BOOLEAN NOT NULL,
        is_randomized_order BOOLEAN NOT NULL,
        is_typing BOOLEAN NOT NULL,
        created_at TIMESTAMP(6) NOT NULL,
        group_id BIGINT NOT NULL,
        id BIGSERIAL NOT NULL,
        deck_name VARCHAR(255) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE groups (
        id BIGSERIAL NOT NULL,
        local_user_id BIGINT NOT NULL,
        group_name VARCHAR(128) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE local_user (
        created_at TIMESTAMP(6) NOT NULL,
        id BIGSERIAL NOT NULL,
        username VARCHAR(60) NOT NULL,
        email VARCHAR(320) NOT NULL UNIQUE,
        password VARCHAR(1000) NOT NULL,
        first_name VARCHAR(255) NOT NULL,
        last_name VARCHAR(255) NOT NULL,
        PRIMARY KEY (id)
);

CREATE TABLE user_progression_history (
        high_indication_count INTEGER,
        low_indication_count INTEGER,
        mid_indication_count INTEGER,
        very_low_indication_count INTEGER,
        created_date TIMESTAMP(6),
        group_id BIGINT NOT NULL,
        id BIGSERIAL NOT NULL,
        last_modified_date TIMESTAMP(6),
        PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS card
    ADD CONSTRAINT card_deck_fk FOREIGN KEY (deck_id) REFERENCES deck ON DELETE CASCADE;

ALTER TABLE IF EXISTS deck
    ADD CONSTRAINT deck_group_fk FOREIGN KEY (group_id) REFERENCES groups ON DELETE CASCADE;

ALTER TABLE IF EXISTS groups
    ADD CONSTRAINT groups_local_user_fk FOREIGN KEY (local_user_id) REFERENCES local_user ON DELETE CASCADE;

ALTER TABLE IF EXISTS user_progression_history
    ADD CONSTRAINT user_progression_history_group_id_fk FOREIGN KEY (group_id) REFERENCES groups ON DELETE CASCADE;
