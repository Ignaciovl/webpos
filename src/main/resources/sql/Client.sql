-- Sequence: seq_client_id

-- DROP SEQUENCE seq_client_id;

CREATE SEQUENCE seq_client_id;

-- Table: client

-- DROP TABLE client;

CREATE TABLE client
(
  id integer NOT NULL DEFAULT nextval('seq_client_id'),
  name character varying(255) NOT NULL,
  id_number character varying(15) NOT NULL,
  contact_number character varying(255),
  email character varying(255),
  address character varying(255),
  CONSTRAINT client_pkey PRIMARY KEY (id),
  CONSTRAINT client_id_number_key UNIQUE (id_number)
);