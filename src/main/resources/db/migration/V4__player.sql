
CREATE TABLE gameplay.health
(
    id serial NOT NULL,
    lifes integer,
    seconds_restore_life bigint,
    timeutcsaving bigint,
    timeutcnow bigint,
    CONSTRAINT uk_player_health_id UNIQUE (id)
);

CREATE TABLE gameplay.player
(
    id serial NOT NULL,
    name text NOT NULL,
    url_avatar text NOT NULL,
    health_id bigint NOT NULL references gameplay.health (id),
    CONSTRAINT uk_player_id UNIQUE (id)
);

