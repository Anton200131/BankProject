--
-- PostgreSQL database dump
--

\restrict i01yEkQqQSiCHmIGwDQKW4StLcYhOXTB7kzwbosL6uk2L1dJZITxSGPyBWz1YfE

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2026-03-31 21:39:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 24690)
-- Name: operations; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.operations (
    operation_id integer NOT NULL,
    user_id bigint NOT NULL,
    operation_type integer NOT NULL,
    amount integer NOT NULL,
    operation_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    receiver_id bigint
);


--
-- TOC entry 218 (class 1259 OID 24689)
-- Name: operations_operation_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.operations_operation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4908 (class 0 OID 0)
-- Dependencies: 218
-- Name: operations_operation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.operations_operation_id_seq OWNED BY public.operations.operation_id;


--
-- TOC entry 217 (class 1259 OID 24683)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    balance numeric(15,2) DEFAULT 0.00 NOT NULL
);


--
-- TOC entry 4747 (class 2604 OID 24693)
-- Name: operations operation_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operations ALTER COLUMN operation_id SET DEFAULT nextval('public.operations_operation_id_seq'::regclass);


--
-- TOC entry 4902 (class 0 OID 24690)
-- Dependencies: 219
-- Data for Name: operations; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.operations (operation_id, user_id, operation_type, amount, operation_date, receiver_id) FROM stdin;
1	1	1	700	2026-03-31 18:49:59.101245	\N
2	1	1	700	2026-03-31 18:49:59.101245	\N
3	1	1	700	2026-03-31 18:49:59.101245	\N
4	1	1	700	2026-03-31 18:49:59.101245	\N
5	1	1	700	2026-03-31 18:49:59.101245	\N
6	1	1	700	2026-03-31 19:19:43.672215	\N
7	1	1	700	2026-03-31 19:19:45.076056	\N
8	1	1	700	2026-03-31 19:19:45.712829	\N
9	1	1	700	2026-03-31 19:19:46.158028	\N
10	1	3	500	2026-03-31 19:23:30.539121	2
11	1	3	500	2026-03-31 19:24:10.420059	2
12	1	3	100	2026-03-31 19:39:49.429676	2
13	1	3	100	2026-03-31 19:45:53.453954	2
\.


--
-- TOC entry 4900 (class 0 OID 24683)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.users (user_id, balance) FROM stdin;
1	6900.00
2	1200.00
\.


--
-- TOC entry 4909 (class 0 OID 0)
-- Dependencies: 218
-- Name: operations_operation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.operations_operation_id_seq', 13, true);


--
-- TOC entry 4752 (class 2606 OID 24695)
-- Name: operations operations_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operations
    ADD CONSTRAINT operations_pkey PRIMARY KEY (operation_id);


--
-- TOC entry 4750 (class 2606 OID 24688)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4753 (class 2606 OID 24702)
-- Name: operations fk_receiver; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operations
    ADD CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES public.users(user_id);


--
-- TOC entry 4754 (class 2606 OID 24696)
-- Name: operations fk_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.operations
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users(user_id);


-- Completed on 2026-03-31 21:39:17

--
-- PostgreSQL database dump complete
--

\unrestrict i01yEkQqQSiCHmIGwDQKW4StLcYhOXTB7kzwbosL6uk2L1dJZITxSGPyBWz1YfE

