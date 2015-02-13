-- Sequence: seq_employee_id

-- DROP SEQUENCE seq_employee_id;

CREATE SEQUENCE seq_employee_id;

-- Table: employee

-- DROP TABLE employee;

CREATE TABLE employee
(
  id integer NOT NULL DEFAULT nextval('seq_employee_id'),
  name character varying(255) NOT NULL,
  id_number character varying(15) NOT NULL,
  contact_number character varying(255),
  email character varying(255),
  address character varying(255),
  position character varying(255),
  CONSTRAINT employee_pkey PRIMARY KEY (id),
  CONSTRAINT employee_id_number_key UNIQUE (id_number)
);