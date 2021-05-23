DROP TABLE IF EXISTS campaign_statistics;

CREATE TABLE campaign_statistics (
    id INT NOT NULL PRIMARY KEY,
    datasource VARCHAR NOT NULL,
    campaign_name VARCHAR NOT NULL,
    date DATE NOT NULL,
    clicks INT NOT NULL,
    impressions INT NOT NULL
);