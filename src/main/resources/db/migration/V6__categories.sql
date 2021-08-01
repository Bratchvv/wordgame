
CREATE TABLE management.game_categories
(
    id serial NOT NULL,
    date timestamp without time zone NOT NULL,
    data text NOT NULL,
    active boolean NOT NULL default false
);
