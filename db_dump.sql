--
-- PostgreSQL database dump
--

\restrict Sl05WK0c46UE5mbtSQtfFZUjADLeFTtZkHAVwF7bTZ2EOOQUikfy4BSKiuUadbc

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2026-03-31 17:57:06

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
-- TOC entry 217 (class 1259 OID 24683)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    user_id bigint NOT NULL,
    balance numeric(15,2) DEFAULT 0.00 NOT NULL
);


--
-- TOC entry 4889 (class 0 OID 24683)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.users (user_id, balance) FROM stdin;
1	1800.00
\.


--
-- TOC entry 4743 (class 2606 OID 24688)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


-- Completed on 2026-03-31 17:57:06

--
-- PostgreSQL database dump complete
--

\unrestrict Sl05WK0c46UE5mbtSQtfFZUjADLeFTtZkHAVwF7bTZ2EOOQUikfy4BSKiuUadbc

