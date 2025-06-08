CREATE TABLE IF NOT EXISTS timezones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    create_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP,
    zone_name VARCHAR(255),
    gmt_offset INT,
    gmt_offset_name VARCHAR(255),
    abbreviation VARCHAR(50),
    tz_name VARCHAR(255),
    country_id BIGINT,
    CONSTRAINT fk_timezone_country FOREIGN KEY (country_id) REFERENCES countries(id) ON DELETE CASCADE
);
