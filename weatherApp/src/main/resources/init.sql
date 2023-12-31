DROP TABLE IF EXISTS town_weather CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS current_weather CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS user_check CASCADE;

CREATE TABLE town_weather (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name varchar(255),
	temp varchar(10),
	feels_like varchar(10),
	cloud varchar(10)
);

CREATE TABLE location (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	region varchar(100),
	country varchar(100)
);

CREATE TABLE current_weather (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	location_id bigint REFERENCES location (id) ON DELETE CASCADE,
	temp varchar(100),
	feels_like varchar(100),
	cloud varchar(100)
);

CREATE TABLE users (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name varchar(100),
	region varchar(100),
	email varchar(100),
	pass varchar(100)
);

INSERT INTO users (name, region, email, pass) VALUES ('Ivan', 'moscow', 'mail@mail', '123');
INSERT INTO users (name, region, email, pass) VALUES ('Petr', 'pskov', 'ya@ya', '123');

CREATE TABLE user_check (
	user_id bigint,
	check_id bigint
);
INSERT INTO user_check (user_id, check_id) VALUES (11, 12);

