-- Table: public.account

-- DROP TABLE public.account;

CREATE TABLE account
(
  id bigint NOT NULL,
  account_number character varying(255) NOT NULL,
  balance bigint NOT NULL,
  is_active boolean,
  person_id bigint NOT NULL,
  PRIMARY KEY (id)
);

  
--data insertion  
INSERT INTO account(
            id, account_number, balance, is_active, person_id)
    VALUES (101, 8776672, 1000, true, 1);

INSERT INTO account(
            id, account_number, balance, is_active, person_id)
    VALUES (102, 36435552, 0, true, 2);

INSERT INTO account(
            id, account_number, balance, is_active, person_id)
    VALUES (103, 754632562, 500, true, 3);

INSERT INTO account(
            id, account_number, balance, is_active, person_id)
    VALUES (104, 3473265, 220, true, 4);
	
	
	
	
-- Table: public.card

-- DROP TABLE public.card;

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
  PRIMARY KEY (id)

--       CONSTRAINT card_pkey PRIMARY KEY (id),
--   CONSTRAINT fk8v67eys6tqflsm6hrdgru2phu FOREIGN KEY (account_id)
--       REFERENCES account (id) MATCH SIMPLE
--       ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE card
    ADD FOREIGN KEY (account_id)
        REFERENCES account (id);

	
	
-- CARD DATA INSERTION	
INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count)
    VALUES (101, 6280231451904303, 199, '2023-11-11', true, 1233, 101, 0);

INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count)
    VALUES (102, 628023145234765, 342, '2022-11-11', false, 1111, 102, 0);

INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count)
    VALUES (103, 628023145412347, 202, '2020-11-11', true, 7654, 101, 0);

INSERT INTO card(
            id, card_number, cvv2, expire_date, is_active, pin, account_id, incorrect_pin_count)
    VALUES (104, 6280231476123412, 239, '2022-11-11', true, 1233, 101, 0);
