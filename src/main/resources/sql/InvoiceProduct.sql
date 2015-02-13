-- Table: invoice_product

-- DROP TABLE invoice_product;

CREATE TABLE invoice_product
(
  invoice_id integer NOT NULL REFERENCES invoice(id),
  product_code character varying(10) NOT NULL,
  product_name character varying(50) NOT NULL,
  department_id integer NOT NULL REFERENCES department(id),
  quantity integer NOT NULL,
  price numeric(13,4) NOT NULL,
  CONSTRAINT invoice_product_pkey PRIMARY KEY (invoice_id, product_code)
);