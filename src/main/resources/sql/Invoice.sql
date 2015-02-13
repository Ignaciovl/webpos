-- Sequence: seq_invoice_id

-- DROP SEQUENCE seq_invoice_id;

CREATE SEQUENCE seq_invoice_id;

-- Table: invoice

-- DROP TABLE invoice;

CREATE TABLE invoice
(
  id integer NOT NULL DEFAULT nextval('seq_invoice_id'),
  created timestamp NOT NULL,
  employee_id integer NOT NULL REFERENCES employee(id),
  client_id integer NOT NULL REFERENCES client(id),
  status character varying(15) NOT NULL,
  payment_method character varying(10),
  total numeric(13,4),
  CONSTRAINT invoice_pkey PRIMARY KEY (id)
);