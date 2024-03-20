SELECT 
    	cp.id_partida AS id_partida,
    	cp.id_carta AS id_carta,
    	cp.utilizada AS carta_utilizada,
    	p.rounds_vencidos_jogador AS rounds_vencidos_jogador,
    	p.rounds_vencidos_cpu AS rounds_vencidos_cpu,
    	p.rounds_empatados AS rounds_empatados,
    	p.resultado AS resultado,
    	p.forca_utilizada AS forca_utilizada,
    	p.inteligencia_utilizada AS inteligencia_utilizada,
    	p.velocidade_utilidade AS velocidade_utilizada,
		cp.do_jogador AS do_jogador,
    	c.nome AS nome,
    	c.forca AS forca,
    	c.inteligencia AS inteligencia,
    	c.velocidade AS velocidade
FROM 
    	carta_partida cp
JOIN 
    	partida p ON cp.id_partida = p.id
JOIN 
    	carta c ON cp.id_carta = c.id
WHERE 
    	cp.id_partida = (
		SELECT 
            	MAX(id) 
        	FROM 
            	partida
	);
