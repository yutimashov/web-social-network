--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 17.1

-- Started on 2025-03-29 19:10:19

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

--
-- TOC entry 6 (class 2615 OID 99222)
-- Name: account_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA account_data;


ALTER SCHEMA account_data OWNER TO postgres;

--
-- TOC entry 7 (class 2615 OID 99223)
-- Name: friend_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA friend_data;


ALTER SCHEMA friend_data OWNER TO postgres;

--
-- TOC entry 8 (class 2615 OID 99224)
-- Name: group_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA group_data;


ALTER SCHEMA group_data OWNER TO postgres;

--
-- TOC entry 9 (class 2615 OID 99225)
-- Name: message_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA message_data;


ALTER SCHEMA message_data OWNER TO postgres;

--
-- TOC entry 10 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2 (class 3079 OID 99534)
-- Name: pg_trgm; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pg_trgm WITH SCHEMA account_data;


--
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION pg_trgm; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pg_trgm IS 'text similarity measurement and index searching based on trigrams';


--
-- TOC entry 237 (class 1255 OID 99226)
-- Name: generate_phone_number(); Type: FUNCTION; Schema: account_data; Owner: postgres
--

CREATE FUNCTION account_data.generate_phone_number() RETURNS text
    LANGUAGE plpgsql
    AS $$
DECLARE
    prefix TEXT;
    number TEXT;
BEGIN
    prefix := '+375' || (ARRAY ['25', '29', '33', '44', '17'])[1 + floor(random() * 5)];
    number := (floor(random() * 9000000) + 1000000)::TEXT;
    RETURN prefix || number;
END;
$$;


ALTER FUNCTION account_data.generate_phone_number() OWNER TO postgres;

--
-- TOC entry 238 (class 1255 OID 99227)
-- Name: random_first_name(); Type: FUNCTION; Schema: account_data; Owner: postgres
--

CREATE FUNCTION account_data.random_first_name() RETURNS text
    LANGUAGE plpgsql
    AS $$
DECLARE
    names TEXT[] := ARRAY ['Ivan', 'Petr', 'Dmitriy', 'Anton', 'Egor', 'Vasiliy', 'Yuriy', 'Sergey', 'Vitaliy', 'Artur',
        'Semen', 'Mihail', 'Eduard', 'Aleksandr', 'Nikolay', 'Pavel', 'Aleksey', 'Anna', 'Elena', 'Olga', 'Tatiana',
        'Irina', 'Ekaterina', 'Maria', 'Lisa', 'Yulia', 'Stanislav', 'Alena', 'Denis'];
BEGIN
    RETURN names[1 + floor(random() * array_length(names, 1))];
END;
$$;


ALTER FUNCTION account_data.random_first_name() OWNER TO postgres;

--
-- TOC entry 239 (class 1255 OID 99228)
-- Name: random_last_name(); Type: FUNCTION; Schema: account_data; Owner: postgres
--

CREATE FUNCTION account_data.random_last_name() RETURNS text
    LANGUAGE plpgsql
    AS $$
DECLARE
    names TEXT[] := ARRAY ['Grib', 'Stanislavchenko', 'Philin', 'Semenko', 'Meher', 'Gonchar', 'Nasteruk', 'Medved',
        'Nikonenko', 'Pavelchitelli', 'German', 'Leshenko', 'Petrenko', 'Zinolenko', 'Fedorenko', 'Onishenko',
        'Plovets', 'Yukki', 'Yabloko', 'Kalinichenko', 'Grom', 'Prohor', 'Telpuk', 'Blok', 'Rimchenko', 'Mirobor',
        'Kalenko', 'Semenko'];
BEGIN
    RETURN names[1 + floor(random() * array_length(names, 1))];
END;
$$;


ALTER FUNCTION account_data.random_last_name() OWNER TO postgres;

--
-- TOC entry 271 (class 1255 OID 99621)
-- Name: random_birth_date(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.random_birth_date() RETURNS date
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN '1900-01-01'::date +
           FLOOR(RANDOM() * (('2024-01-01'::date - '1900-01-01'::date)::integer))::integer;
END;
$$;


ALTER FUNCTION public.random_birth_date() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 99229)
-- Name: accounts; Type: TABLE; Schema: account_data; Owner: postgres
--

CREATE TABLE account_data.accounts (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    middle_name character varying(50) DEFAULT NULL::character varying,
    birth_date date,
    personal_address character varying(100) DEFAULT NULL::character varying,
    work_address character varying(100) DEFAULT NULL::character varying,
    email character varying(100) NOT NULL,
    icq character varying(50) DEFAULT NULL::character varying,
    skype character varying(50) DEFAULT NULL::character varying,
    additional_info text,
    registration_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    role_type character varying(32) DEFAULT 'REGULAR'::character varying,
    avatar bytea,
    fullname text GENERATED ALWAYS AS (lower((((first_name)::text || ' '::text) || (last_name)::text))) STORED
);


ALTER TABLE account_data.accounts OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 99241)
-- Name: account_id_seq; Type: SEQUENCE; Schema: account_data; Owner: postgres
--

CREATE SEQUENCE account_data.account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE account_data.account_id_seq OWNER TO postgres;

--
-- TOC entry 4948 (class 0 OID 0)
-- Dependencies: 221
-- Name: account_id_seq; Type: SEQUENCE OWNED BY; Schema: account_data; Owner: postgres
--

ALTER SEQUENCE account_data.account_id_seq OWNED BY account_data.accounts.id;


--
-- TOC entry 222 (class 1259 OID 99242)
-- Name: account_passwords; Type: TABLE; Schema: account_data; Owner: postgres
--

CREATE TABLE account_data.account_passwords (
    hash_password character varying(72),
    id integer NOT NULL
);


ALTER TABLE account_data.account_passwords OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 99245)
-- Name: account_passwords_id_seq; Type: SEQUENCE; Schema: account_data; Owner: postgres
--

CREATE SEQUENCE account_data.account_passwords_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE account_data.account_passwords_id_seq OWNER TO postgres;

--
-- TOC entry 4949 (class 0 OID 0)
-- Dependencies: 223
-- Name: account_passwords_id_seq; Type: SEQUENCE OWNED BY; Schema: account_data; Owner: postgres
--

ALTER SEQUENCE account_data.account_passwords_id_seq OWNED BY account_data.account_passwords.id;


--
-- TOC entry 224 (class 1259 OID 99246)
-- Name: account_phones; Type: TABLE; Schema: account_data; Owner: postgres
--

CREATE TABLE account_data.account_phones (
    id integer NOT NULL,
    account_id integer,
    phone_type character varying(10) NOT NULL,
    phone_number character varying(13) NOT NULL
);


ALTER TABLE account_data.account_phones OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 99249)
-- Name: account_phones_id_seq; Type: SEQUENCE; Schema: account_data; Owner: postgres
--

CREATE SEQUENCE account_data.account_phones_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE account_data.account_phones_id_seq OWNER TO postgres;

--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 225
-- Name: account_phones_id_seq; Type: SEQUENCE OWNED BY; Schema: account_data; Owner: postgres
--

ALTER SEQUENCE account_data.account_phones_id_seq OWNED BY account_data.account_phones.id;


--
-- TOC entry 226 (class 1259 OID 99250)
-- Name: friendship; Type: TABLE; Schema: friend_data; Owner: postgres
--

CREATE TABLE friend_data.friendship (
    id_1 integer NOT NULL,
    id_2 integer NOT NULL,
    status boolean DEFAULT false NOT NULL,
    requester_id integer,
    accepter_id integer,
    CONSTRAINT friendship_check CHECK ((id_1 < id_2))
);


ALTER TABLE friend_data.friendship OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 99255)
-- Name: groups; Type: TABLE; Schema: group_data; Owner: postgres
--

CREATE TABLE group_data.groups (
    id integer NOT NULL,
    owner_id integer,
    group_name character varying(255) NOT NULL,
    description text,
    registration_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    avatar bytea
);


ALTER TABLE group_data.groups OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 99261)
-- Name: group_id_seq; Type: SEQUENCE; Schema: group_data; Owner: postgres
--

CREATE SEQUENCE group_data.group_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE group_data.group_id_seq OWNER TO postgres;

--
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 228
-- Name: group_id_seq; Type: SEQUENCE OWNED BY; Schema: group_data; Owner: postgres
--

ALTER SEQUENCE group_data.group_id_seq OWNED BY group_data.groups.id;


--
-- TOC entry 229 (class 1259 OID 99262)
-- Name: group_members; Type: TABLE; Schema: group_data; Owner: postgres
--

CREATE TABLE group_data.group_members (
    id integer NOT NULL,
    account_id integer,
    group_id integer,
    is_admin boolean DEFAULT false NOT NULL,
    is_member boolean DEFAULT false NOT NULL,
    registration_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE group_data.group_members OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 99268)
-- Name: group_members_id_seq; Type: SEQUENCE; Schema: group_data; Owner: postgres
--

CREATE SEQUENCE group_data.group_members_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE group_data.group_members_id_seq OWNER TO postgres;

--
-- TOC entry 4952 (class 0 OID 0)
-- Dependencies: 230
-- Name: group_members_id_seq; Type: SEQUENCE OWNED BY; Schema: group_data; Owner: postgres
--

ALTER SEQUENCE group_data.group_members_id_seq OWNED BY group_data.group_members.id;


--
-- TOC entry 231 (class 1259 OID 99269)
-- Name: group_messages; Type: TABLE; Schema: message_data; Owner: postgres
--

CREATE TABLE message_data.group_messages (
    id integer NOT NULL,
    account_author_id integer NOT NULL,
    group_id integer NOT NULL,
    message_text text,
    message_image bytea,
    creation_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE message_data.group_messages OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 99275)
-- Name: group_messages_id_seq; Type: SEQUENCE; Schema: message_data; Owner: postgres
--

CREATE SEQUENCE message_data.group_messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE message_data.group_messages_id_seq OWNER TO postgres;

--
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 232
-- Name: group_messages_id_seq; Type: SEQUENCE OWNED BY; Schema: message_data; Owner: postgres
--

ALTER SEQUENCE message_data.group_messages_id_seq OWNED BY message_data.group_messages.id;


--
-- TOC entry 233 (class 1259 OID 99276)
-- Name: personal_messages; Type: TABLE; Schema: message_data; Owner: postgres
--

CREATE TABLE message_data.personal_messages (
    id integer NOT NULL,
    account_author_id integer NOT NULL,
    destination_id integer NOT NULL,
    message_text text,
    message_image bytea,
    creation_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE message_data.personal_messages OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 99282)
-- Name: personal_messages_id_seq; Type: SEQUENCE; Schema: message_data; Owner: postgres
--

CREATE SEQUENCE message_data.personal_messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE message_data.personal_messages_id_seq OWNER TO postgres;

--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 234
-- Name: personal_messages_id_seq; Type: SEQUENCE OWNED BY; Schema: message_data; Owner: postgres
--

ALTER SEQUENCE message_data.personal_messages_id_seq OWNED BY message_data.personal_messages.id;


--
-- TOC entry 235 (class 1259 OID 99283)
-- Name: personal_wall_messages; Type: TABLE; Schema: message_data; Owner: postgres
--

CREATE TABLE message_data.personal_wall_messages (
    id integer NOT NULL,
    account_author_id integer NOT NULL,
    account_receiver_id integer NOT NULL,
    message_text text,
    message_image bytea,
    creation_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE message_data.personal_wall_messages OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 99289)
-- Name: personal_wall_messages_id_seq; Type: SEQUENCE; Schema: message_data; Owner: postgres
--

CREATE SEQUENCE message_data.personal_wall_messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE message_data.personal_wall_messages_id_seq OWNER TO postgres;

--
-- TOC entry 4955 (class 0 OID 0)
-- Dependencies: 236
-- Name: personal_wall_messages_id_seq; Type: SEQUENCE OWNED BY; Schema: message_data; Owner: postgres
--

ALTER SEQUENCE message_data.personal_wall_messages_id_seq OWNED BY message_data.personal_wall_messages.id;


--
-- TOC entry 4739 (class 2604 OID 99290)
-- Name: account_passwords id; Type: DEFAULT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.account_passwords ALTER COLUMN id SET DEFAULT nextval('account_data.account_passwords_id_seq'::regclass);


--
-- TOC entry 4740 (class 2604 OID 99291)
-- Name: account_phones id; Type: DEFAULT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.account_phones ALTER COLUMN id SET DEFAULT nextval('account_data.account_phones_id_seq'::regclass);


--
-- TOC entry 4730 (class 2604 OID 99292)
-- Name: accounts id; Type: DEFAULT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.accounts ALTER COLUMN id SET DEFAULT nextval('account_data.account_id_seq'::regclass);


--
-- TOC entry 4744 (class 2604 OID 99293)
-- Name: group_members id; Type: DEFAULT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.group_members ALTER COLUMN id SET DEFAULT nextval('group_data.group_members_id_seq'::regclass);


--
-- TOC entry 4742 (class 2604 OID 99294)
-- Name: groups id; Type: DEFAULT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.groups ALTER COLUMN id SET DEFAULT nextval('group_data.group_id_seq'::regclass);


--
-- TOC entry 4748 (class 2604 OID 99295)
-- Name: group_messages id; Type: DEFAULT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.group_messages ALTER COLUMN id SET DEFAULT nextval('message_data.group_messages_id_seq'::regclass);


--
-- TOC entry 4750 (class 2604 OID 99296)
-- Name: personal_messages id; Type: DEFAULT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_messages ALTER COLUMN id SET DEFAULT nextval('message_data.personal_messages_id_seq'::regclass);


--
-- TOC entry 4752 (class 2604 OID 99297)
-- Name: personal_wall_messages id; Type: DEFAULT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_wall_messages ALTER COLUMN id SET DEFAULT nextval('message_data.personal_wall_messages_id_seq'::regclass);


--
-- TOC entry 4761 (class 2606 OID 99299)
-- Name: account_passwords account_passwords_pkey; Type: CONSTRAINT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.account_passwords
    ADD CONSTRAINT account_passwords_pkey PRIMARY KEY (id);


--
-- TOC entry 4764 (class 2606 OID 99301)
-- Name: account_phones account_phones_pkey; Type: CONSTRAINT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.account_phones
    ADD CONSTRAINT account_phones_pkey PRIMARY KEY (id);


--
-- TOC entry 4756 (class 2606 OID 99303)
-- Name: accounts account_pkey; Type: CONSTRAINT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.accounts
    ADD CONSTRAINT account_pkey PRIMARY KEY (id);


--
-- TOC entry 4769 (class 2606 OID 99305)
-- Name: friendship friendship_pkey; Type: CONSTRAINT; Schema: friend_data; Owner: postgres
--

ALTER TABLE ONLY friend_data.friendship
    ADD CONSTRAINT friendship_pkey PRIMARY KEY (id_1, id_2);


--
-- TOC entry 4772 (class 2606 OID 99308)
-- Name: groups group_group_name_key; Type: CONSTRAINT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.groups
    ADD CONSTRAINT group_group_name_key UNIQUE (group_name);


--
-- TOC entry 4776 (class 2606 OID 99310)
-- Name: group_members group_members_pkey; Type: CONSTRAINT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.group_members
    ADD CONSTRAINT group_members_pkey PRIMARY KEY (id);


--
-- TOC entry 4774 (class 2606 OID 99312)
-- Name: groups group_pkey; Type: CONSTRAINT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.groups
    ADD CONSTRAINT group_pkey PRIMARY KEY (id);


--
-- TOC entry 4778 (class 2606 OID 99314)
-- Name: group_messages group_messages_pkey; Type: CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.group_messages
    ADD CONSTRAINT group_messages_pkey PRIMARY KEY (id);


--
-- TOC entry 4780 (class 2606 OID 99316)
-- Name: personal_messages personal_messages_pkey; Type: CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_messages
    ADD CONSTRAINT personal_messages_pkey PRIMARY KEY (id);


--
-- TOC entry 4782 (class 2606 OID 99318)
-- Name: personal_wall_messages personal_wall_messages_pkey; Type: CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_wall_messages
    ADD CONSTRAINT personal_wall_messages_pkey PRIMARY KEY (id);


--
-- TOC entry 4762 (class 1259 OID 99319)
-- Name: account_phones_account_id_phone_type_idx; Type: INDEX; Schema: account_data; Owner: postgres
--

CREATE INDEX account_phones_account_id_phone_type_idx ON account_data.account_phones USING btree (account_id, phone_type);


--
-- TOC entry 4757 (class 1259 OID 99320)
-- Name: accounts_email_idx; Type: INDEX; Schema: account_data; Owner: postgres
--

CREATE INDEX accounts_email_idx ON account_data.accounts USING btree (email);


--
-- TOC entry 4758 (class 1259 OID 99617)
-- Name: idx_accounts_full_name_trgm; Type: INDEX; Schema: account_data; Owner: postgres
--

CREATE INDEX idx_accounts_full_name_trgm ON account_data.accounts USING gin (fullname account_data.gin_trgm_ops);


--
-- TOC entry 4759 (class 1259 OID 99622)
-- Name: idx_birth_month_day; Type: INDEX; Schema: account_data; Owner: postgres
--

CREATE INDEX idx_birth_month_day ON account_data.accounts USING btree (EXTRACT(month FROM birth_date), EXTRACT(day FROM birth_date));


--
-- TOC entry 4765 (class 1259 OID 99620)
-- Name: friendship_accepter_id_status_requester_id_idx; Type: INDEX; Schema: friend_data; Owner: postgres
--

CREATE INDEX friendship_accepter_id_status_requester_id_idx ON friend_data.friendship USING btree (accepter_id, status, requester_id);


--
-- TOC entry 4766 (class 1259 OID 99403)
-- Name: friendship_id_1_status_id_2_idx; Type: INDEX; Schema: friend_data; Owner: postgres
--

CREATE INDEX friendship_id_1_status_id_2_idx ON friend_data.friendship USING btree (id_1, status, id_2);


--
-- TOC entry 4767 (class 1259 OID 99404)
-- Name: friendship_id_2_status_id_1_idx; Type: INDEX; Schema: friend_data; Owner: postgres
--

CREATE INDEX friendship_id_2_status_id_1_idx ON friend_data.friendship USING btree (id_2, status, id_1);


--
-- TOC entry 4770 (class 1259 OID 99619)
-- Name: friendship_requester_id_status_accepter_id_idx; Type: INDEX; Schema: friend_data; Owner: postgres
--

CREATE INDEX friendship_requester_id_status_accepter_id_idx ON friend_data.friendship USING btree (requester_id, status, accepter_id);


--
-- TOC entry 4783 (class 2606 OID 99323)
-- Name: account_passwords account_passwords___fk; Type: FK CONSTRAINT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.account_passwords
    ADD CONSTRAINT account_passwords___fk FOREIGN KEY (id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4784 (class 2606 OID 99328)
-- Name: account_phones account_phones_account_id_fkey; Type: FK CONSTRAINT; Schema: account_data; Owner: postgres
--

ALTER TABLE ONLY account_data.account_phones
    ADD CONSTRAINT account_phones_account_id_fkey FOREIGN KEY (account_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4785 (class 2606 OID 99333)
-- Name: friendship friendship_accepter_id_fkey; Type: FK CONSTRAINT; Schema: friend_data; Owner: postgres
--

ALTER TABLE ONLY friend_data.friendship
    ADD CONSTRAINT friendship_accepter_id_fkey FOREIGN KEY (accepter_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4786 (class 2606 OID 99338)
-- Name: friendship friendship_id_1_fkey; Type: FK CONSTRAINT; Schema: friend_data; Owner: postgres
--

ALTER TABLE ONLY friend_data.friendship
    ADD CONSTRAINT friendship_id_1_fkey FOREIGN KEY (id_1) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4787 (class 2606 OID 99343)
-- Name: friendship friendship_id_2_fkey; Type: FK CONSTRAINT; Schema: friend_data; Owner: postgres
--

ALTER TABLE ONLY friend_data.friendship
    ADD CONSTRAINT friendship_id_2_fkey FOREIGN KEY (id_2) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4788 (class 2606 OID 99348)
-- Name: friendship friendship_requester_id_fkey; Type: FK CONSTRAINT; Schema: friend_data; Owner: postgres
--

ALTER TABLE ONLY friend_data.friendship
    ADD CONSTRAINT friendship_requester_id_fkey FOREIGN KEY (requester_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4790 (class 2606 OID 99353)
-- Name: group_members group_members_account_id_fkey; Type: FK CONSTRAINT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.group_members
    ADD CONSTRAINT group_members_account_id_fkey FOREIGN KEY (account_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4791 (class 2606 OID 99358)
-- Name: group_members group_members_group_id_fkey; Type: FK CONSTRAINT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.group_members
    ADD CONSTRAINT group_members_group_id_fkey FOREIGN KEY (group_id) REFERENCES group_data.groups(id) ON DELETE CASCADE;


--
-- TOC entry 4789 (class 2606 OID 99363)
-- Name: groups group_owner_id_fkey; Type: FK CONSTRAINT; Schema: group_data; Owner: postgres
--

ALTER TABLE ONLY group_data.groups
    ADD CONSTRAINT group_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4792 (class 2606 OID 99368)
-- Name: group_messages group_messages_account_author_id_fkey; Type: FK CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.group_messages
    ADD CONSTRAINT group_messages_account_author_id_fkey FOREIGN KEY (account_author_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4793 (class 2606 OID 99373)
-- Name: group_messages group_messages_group_id_fkey; Type: FK CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.group_messages
    ADD CONSTRAINT group_messages_group_id_fkey FOREIGN KEY (group_id) REFERENCES group_data.groups(id) ON DELETE CASCADE;


--
-- TOC entry 4794 (class 2606 OID 99378)
-- Name: personal_messages personal_messages_account_author_id_fkey; Type: FK CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_messages
    ADD CONSTRAINT personal_messages_account_author_id_fkey FOREIGN KEY (account_author_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4795 (class 2606 OID 99383)
-- Name: personal_messages personal_messages_destination_id_fkey; Type: FK CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_messages
    ADD CONSTRAINT personal_messages_destination_id_fkey FOREIGN KEY (destination_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4796 (class 2606 OID 99388)
-- Name: personal_wall_messages personal_wall_messages_account_author_id_fkey; Type: FK CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_wall_messages
    ADD CONSTRAINT personal_wall_messages_account_author_id_fkey FOREIGN KEY (account_author_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4797 (class 2606 OID 99393)
-- Name: personal_wall_messages personal_wall_messages_account_receiver_id_fkey; Type: FK CONSTRAINT; Schema: message_data; Owner: postgres
--

ALTER TABLE ONLY message_data.personal_wall_messages
    ADD CONSTRAINT personal_wall_messages_account_receiver_id_fkey FOREIGN KEY (account_receiver_id) REFERENCES account_data.accounts(id) ON DELETE CASCADE;


--
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 10
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2025-03-29 19:10:19

--
-- PostgreSQL database dump complete
--

