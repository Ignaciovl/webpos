-- Sequence: seq_department_id

-- DROP SEQUENCE seq_department_id;

CREATE SEQUENCE seq_department_id;

-- Table: department

-- DROP TABLE department;

CREATE TABLE department
(
  id integer NOT NULL DEFAULT nextval('seq_department_id'),
  code character varying(3) NOT NULL,
  name character varying(30) NOT NULL,
  CONSTRAINT department_pkey PRIMARY KEY (id),
  CONSTRAINT department_code_key UNIQUE (code)
);