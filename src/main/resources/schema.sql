DROP TABLE IF EXISTS campaign_statistics;

CREATE TABLE campaign_statistics
(
    id            SERIAL PRIMARY KEY,
    datasource    VARCHAR   NOT NULL,
    campaign_name VARCHAR   NOT NULL,
    date          DATE      NOT NULL,
    clicks        INT       NOT NULL,
    impressions   INT       NOT NULL
);

CREATE UNIQUE INDEX idx_unique_campaign
    ON campaign_statistics (datasource, campaign_name, date);