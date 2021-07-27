
CREATE TABLE management.game_properties
(
    id serial NOT NULL,
    name text NOT NULL,
    value text NOT NULL,
    label text NOT NULL,
    CONSTRAINT name_params_constraint UNIQUE (name)
);

INSERT INTO management.game_properties(name, value, label) VALUES ('BufferSize', '100', 'Размер буфера результатов');
INSERT INTO management.game_properties(name, value, label) VALUES ('CountRow', '5', 'Кол-во строк');
INSERT INTO management.game_properties(name, value, label) VALUES ('CountColumn', '5', 'Кол-во колонок');
INSERT INTO management.game_properties(name, value, label) VALUES ('MaxIteration', '10', 'Кол-во попыток генерации');
INSERT INTO management.game_properties(name, value, label) VALUES ('MinCountWord', '100', 'Минимальное количество слов, для успешной генерации');
