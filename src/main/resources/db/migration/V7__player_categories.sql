CREATE TABLE player_game_categories
(
    id        bigint AUTO_INCREMENT PRIMARY KEY,
    player_id VARCHAR(255)                        NOT NULL,
    date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data      LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    FOREIGN KEY (player_id) REFERENCES player (id)
);
