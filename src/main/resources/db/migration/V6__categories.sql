CREATE TABLE game_categories
(
    id     bigint AUTO_INCREMENT PRIMARY KEY,
    date   TIMESTAMP        DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data   LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    active boolean NOT NULL default false
);
