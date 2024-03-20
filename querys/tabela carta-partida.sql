CREATE TABLE carta_partida (
		id SERIAL PRIMARY KEY,
		id_partida INTEGER REFERENCES partida(id),
		id_carta INTEGER REFERENCES carta(id),
		do_jogador BOOLEAN,
		utilizada BOOLEAN
);
