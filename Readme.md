``
CREATE TABLE IF NOT EXISTS config_params
(
config_id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1 ),
config_key character varying(100) COLLATE pg_catalog."default",
config_value character varying(100) COLLATE pg_catalog."default",
config_description character varying(100) COLLATE pg_catalog."default",
CONSTRAINT config_params_pkey PRIMARY KEY (config_id)
)

insert into config_params(config_key, config_value, config_description)
values('con.key1', 'value1', 'description1'),
('con.key2', 'value2', 'description1');


``