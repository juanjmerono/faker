CREATE SCHEMA IF NOT EXISTS FAKER;

DROP TABLE IF EXISTS FAKER.USERS_PREFS;
DROP TABLE IF EXISTS FAKER.USERS;

CREATE TABLE FAKER.USERS(
		identificador VARCHAR(36) PRIMARY KEY NOT NULL,
		nombre VARCHAR(100) NOT NULL,
		edad INTEGER NOT NULL,
		pais VARCHAR(100) NOT NULL
	);

CREATE TABLE FAKER.USERS_PREFS(
		nombre VARCHAR(100) NOT NULL,
		cantidad INTEGER NOT NULL,
		idusuario VARCHAR(36) NOT NULL,
		--PRIMARY KEY (idusuario,nombre),
		FOREIGN KEY(idusuario) REFERENCES FAKER.USERS(identificador)
	);