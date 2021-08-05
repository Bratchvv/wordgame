CREATE TABLE IF NOT EXISTS game_properties
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(500) NOT NULL UNIQUE,
    value text         NOT NULL,
    label text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
);

INSERT game_properties(name, value, label)
VALUES ('BufferSize', '100', 'Размер буфера результатов');
INSERT game_properties(name, value, label)
VALUES ('CountRow', '5', 'Кол-во строк');
INSERT game_properties(name, value, label)
VALUES ('CountColumn', '5', 'Кол-во колонок');
INSERT game_properties(name, value, label)
VALUES ('MaxIteration', '10', 'Кол-во попыток генерации');
INSERT game_properties(name, value, label)
VALUES ('MinCountWord', '100', 'Минимальное количество слов, для успешной генерации');
