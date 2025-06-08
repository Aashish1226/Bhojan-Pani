CREATE TABLE IF NOT EXISTS regions
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL UNIQUE,
    wiki_data_id VARCHAR(10)  NOT NULL UNIQUE,
    create_date  TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP    NOT NULL
    );

CREATE TABLE IF NOT EXISTS sub_regions
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(150) NOT NULL UNIQUE,
    wiki_data_id VARCHAR(210) NOT NULL UNIQUE,
    create_date  TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP    NOT NULL,
    region_id    BIGINT,
    CONSTRAINT fk_region_id FOREIGN KEY (region_id) REFERENCES regions (id) ON DELETE CASCADE);
