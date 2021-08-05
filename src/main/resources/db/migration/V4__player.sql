CREATE TABLE IF NOT EXISTS health
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    lifes                INT,
    seconds_restore_life bigint,
    timeutcsaving        bigint,
    timeutcnow           bigint
);

CREATE TABLE IF NOT EXISTS player
(
    id         VARCHAR(255) primary key NOT NULL,
    name       VARCHAR(255)             NOT NULL,
    url_avatar text                     NOT NULL,
    health_id  INT                      NOT NULL,
    FOREIGN KEY (health_id)
        REFERENCES health (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

