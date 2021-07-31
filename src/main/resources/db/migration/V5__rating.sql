
CREATE TABLE statistics.rating_table
(
    id serial NOT NULL,
    name text NOT NULL,
    init_time_utc bigint,
    expire_hours_cycle integer,
    CONSTRAINT uk_rating_table_id UNIQUE (id),
    CONSTRAINT uk_rating_table_name UNIQUE (name)
);

CREATE TABLE statistics.rating_table_data
(
    id serial NOT NULL,
    name text NOT NULL,
    value integer NOT NULL,
    player_id text NOT NULL references gameplay.player (id),
    rating_table_id bigint NOT NULL references statistics.rating_table (id),
    CONSTRAINT uk_player_id UNIQUE (id),
    CONSTRAINT uk_name_player_id UNIQUE (name, player_id)
);

CREATE INDEX IF NOT EXISTS idx_rating_table_name ON statistics.rating_table USING hash (name);

CREATE INDEX IF NOT EXISTS idx_rating_table_data_name ON statistics.rating_table_data USING hash (name);
CREATE INDEX IF NOT EXISTS idx_rating_table_data_player_id ON statistics.rating_table_data USING hash (player_id);
CREATE INDEX IF NOT EXISTS idx_rating_table_data_rating_table_id ON statistics.rating_table_data USING hash (rating_table_id);
