
CREATE TABLE gameplay.player_game_categories
(
    id serial NOT NULL,
    player_id text NOT NULL references gameplay.player(id),
    date timestamp without time zone NOT NULL,
    data text NOT NULL
);
