CREATE TABLE carta (
		id SERIAL PRIMARY KEY,
		nome VARCHAR(100) UNIQUE,
		forca INT,
		inteligencia INT,
		velocidade INT
);