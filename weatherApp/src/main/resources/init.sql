DROP TABLE IF EXISTS town_weather CASCADE;

CREATE TABLE town_weather (
	id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name varchar(255),
	temp varchar(10),
	feels_like varchar(10),
	cloud varchar(10)
);

