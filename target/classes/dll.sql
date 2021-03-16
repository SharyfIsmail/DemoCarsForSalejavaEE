CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID   BIGSERIAL PRIMARY KEY NOT NULL,
    USER_NAME varchar(30)           NOT NULL,
    EMAIL     varchar(30) UNIQUE    NOT NULL,
    PASSWORD  varchar(15)           NOT NULL
);
CREATE TYPE CURRENT_CONDITION AS ENUM ('NEW', 'USED', 'BROKEN');

CREATE TABLE IF NOT EXISTS ADS
(
    AD_ID           BIGSERIAL PRIMARY KEY NOT NULL,
    USER_ID         BIGINT                NOT NULL,
    YEAR            SMALLINT              NOT NULL,
    BRAND           VARCHAR(255)          NOT NULL,
    MODEL           VARCHAR(255)          NOT NULL,
    ENGINE_CAPACITY INT,
    CONDITION       CURRENT_CONDITION     NOT NULL default 'NEW',
    MILEAGE         INT,
    POWER           INT,
    CREATING_DATE   TIMESTAMP             NOT NULL,
    EDIT_DATE       TIMESTAMP             NOT NULL,
    FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PHONES
(
    PHONE_ID BIGSERIAL PRIMARY KEY NOT NULL,
    USER_ID  BIGINT                NOT NULL,
    PHONE    VARCHAR(13)           NOT NULL UNIQUE CHECK (length(PHONES.PHONE) = 13),
    FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID)
        ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS PICS
(
    PIC_ID BIGSERIAL PRIMARY KEY NOT NULL,
    AD_ID  BIGINT                NOT NULL,
    PIC    VARCHAR(255),
    FOREIGN KEY (AD_ID)
        REFERENCES ADS (AD_ID)
        ON DELETE CASCADE
);





