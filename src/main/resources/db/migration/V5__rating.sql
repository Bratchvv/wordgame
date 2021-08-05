CREATE TABLE IF NOT EXISTS rating_table
(
    id                 bigint AUTO_INCREMENT PRIMARY KEY,
    name               VARCHAR(255) NOT NULL UNIQUE,
    init_time_utc      bigint,
    expire_hours_cycle INT,
    timeutcnow         bigint
);

CREATE TABLE IF NOT EXISTS rating_table_data
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    value           INT          NOT NULL,
    player_id       VARCHAR(255) NOT NULL,
    rating_table_id bigint       NOT NULL,
    FOREIGN KEY (player_id) REFERENCES player (id),
    FOREIGN KEY (rating_table_id) REFERENCES rating_table (id)
);


CREATE INDEX idx_rating_table_name ON rating_table (name);

CREATE INDEX idx_rating_table_data_name ON rating_table_data (name);
CREATE INDEX idx_rating_table_data_player_id ON rating_table_data (player_id);
CREATE INDEX idx_rating_table_data_rating_table_id ON rating_table_data (rating_table_id);
