DROP TABLE IF EXISTS town_weather CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS current_weather CASCADE;

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
	country varchar(100),
	local_time varchar(20)
);

CREATE TABLE current_weather (
	id bigint PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	location_id bigint REFERENCES location (id) ON DELETE CASCADE,
	temp varchar(10),
	feels_like varchar(10),
	cloud varchar(10)
);
