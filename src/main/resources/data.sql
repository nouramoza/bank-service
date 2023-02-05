
CREATE TABLE account
(
  id bigint NOT NULL,
  account_number character varying(255) NOT NULL,
  balance bigint NOT NULL,
  is_active boolean,
  person_id bigint NOT NULL,
  OPEN_DATE timestamp without time zone NOT NULL,
  CLOSE_DATE timestamp without time zone,
  PRIMARY KEY (id)
);

  
--data insertion  
INSERT INTO account(
            id, account_number, balance, is_active, person_id, open_date)
    VALUES (101, 8776672, 1000, true, 1, TIMESTAMP '2022-11-11 00:00:00.0');

INSERT INTO account(
            id, account_number, balance, is_active, person_id, open_date)
    VALUES (102, 36435552, 0, true, 2, TIMESTAMP '2023-01-10 00:00:00.0');

INSERT INTO account(
            id, account_number, balance, is_active, person_id, open_date)
    VALUES (103, 754632562, 500, true, 3, TIMESTAMP '2022-01-11 00:00:00.0');

INSERT INTO account(
            id, account_number, balance, is_active, person_id, open_date)
    VALUES (104, 3473265, 220, true, 4, TIMESTAMP '2022-11-11 00:00:00.0');
	


CREATE TABLE card
(
  id bigint NOT NULL,
  card_number character varying(255) NOT NULL,
  cvv2 integer NOT NULL,
  expire_date timestamp without time zone NOT NULL,
  is_active boolean,
  pin character varying(255) NOT NULL,
  account_id bigint NOT NULL,
  incorrect_pin_count integer,
  ISSUE_DATE timestamp without time zone NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE card
    ADD FOREIGN KEY (account_id)
        REFERENCES account (id);

-- CARD DATA INSERTION
INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count, ISSUE_DATE)
    VALUES (101, 6280231451904303, 199, '2023-11-11', true, 1233, 101, 0, TIMESTAMP '2022-11-11 00:00:00.0');

INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count, ISSUE_DATE)
    VALUES (102, 628023145234765, 342, '2023-11-11', false, 1111, 102, 0, TIMESTAMP '2021-11-11 00:00:00.0');

INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count, ISSUE_DATE)
    VALUES (103, 628023145412347, 202, '2020-11-11', true, 7654, 101, 0, TIMESTAMP '2010-11-11 00:00:00.0');

INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count, ISSUE_DATE)
    VALUES (104, 6280231476123412, 239, '2023-11-11', true, 1233, 101, 0, TIMESTAMP '2020-11-11 00:00:00.0');
