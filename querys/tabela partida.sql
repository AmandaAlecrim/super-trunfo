CREATE TABLE partida (
    	id SERIAL PRIMARY KEY,
    	rounds_vencidos_jogador INTEGER,
    	rounds_vencidos_cpu INTEGER,
    	rounds_empatados INTEGER,
    	resultado TEXT,
    	data DATE,
    	forca_utilizada BOOLEAN,
    	inteligencia_utilizada BOOLEAN,
    	velocidade_utilidade BOOLEAN
);
