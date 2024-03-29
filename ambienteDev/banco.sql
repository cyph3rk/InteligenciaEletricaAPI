-- Database: base_dev

BEGIN;

CREATE TABLE IF NOT EXISTS public.endereco
(
    id serial NOT NULL,
    rua text NOT NULL,
    numero text NOT NULL,
    bairro text NOT NULL,
    cidade text NOT NULL,
    estado text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.pessoa
(
    id serial NOT NULL,
    familia_id serial NOT NULL,
    nome text NOT NULL,
    data_nascimento text NOT NULL,
    sexo text NOT NULL,
    codigo_cliente text NOT NULL,
    relacionamento text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.familia
(
    id serial NOT NULL,
    nome text NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.pessoa_endereco
(
    pessoa_id serial NOT NULL,
    endereco_id serial NOT NULL
);

CREATE TABLE IF NOT EXISTS public.equipamento
(
    id serial NOT NULL,
    endereco_id serial NOT NULL,
    nome text NOT NULL,
    modelo text NOT NULL,
    potencia text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.consumo
(
    id serial NOT NULL,
    equipamento_id serial NOT NULL,
    pessoa_id serial,
    consumo text NOT NULL,
    data text NOT NULL,
    PRIMARY KEY (id)
);


ALTER TABLE IF EXISTS public.pessoa_endereco
    ADD FOREIGN KEY (pessoa_id)
    REFERENCES public.pessoa (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.pessoa_endereco
    ADD FOREIGN KEY (endereco_id)
    REFERENCES public.endereco (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.pessoa
    ADD FOREIGN KEY (familia_id)
    REFERENCES public.familia (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.equipamento
    ADD FOREIGN KEY (endereco_id)
    REFERENCES public.endereco (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.consumo
    ADD FOREIGN KEY (equipamento_id)
    REFERENCES public.equipamento (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

ALTER TABLE IF EXISTS public.consumo
    ADD FOREIGN KEY (pessoa_id)
    REFERENCES public.pessoa (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;

END;
