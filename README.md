# Super Trunfo (Console)
Jogo de Super Trunfo em terminal, com persistência em PostgreSQL via JDBC para registrar cartas e histórico de partidas.

### Resumo do projeto
- **Objetivo**: aplicar fundamentos de Java, modelagem de domínio e persistência relacional em um jogo por turnos (jogador vs CPU), registrando partidas e cartas utilizadas.

- **Entregas (features)**:
  - Distribuição e embaralhamento de cartas e execução de uma partida com 3 rodadas (jogador vs CPU).
  - Seleção de atributo por rodada (Força/Inteligência/Velocidade) com bloqueio de reuso na mesma partida.
  - Seleção de carta pelo jogador e lógica simples de escolha da melhor carta pela CPU para o atributo da rodada.
  - Persistência de dados em banco: leitura de cartas, criação/atualização de partidas e registro das cartas usadas em cada partida.
  - Regras de consistência para cadastro de cartas: soma dos atributos \(\le 15\) e nome único.

- **Competências demonstradas**:
  - Modelagem de domínio (entidades e regras de negócio) e separação por camadas (model/dao).
  - Integração com banco relacional via JDBC (PreparedStatement, ResultSet) e uso de sequences.
  - Tratamento de erros com exceções de domínio e validações antes da persistência.
  - Lógica de jogo por turnos e manipulação de coleções (embaralhar, distribuir, remover da mão).

### Stack e requisitos
- **Linguagem**: Java 19 (`maven.compiler.source/target`).
- **Build**: Maven (`pom.xml`).
- **Persistência**: JDBC (DAO manual, sem ORM).
- **Banco**: PostgreSQL (driver `org.postgresql:postgresql`).

### Arquitetura
- **`src/main/java/com/supertrunfo`**:
  - **`App`**: ponto de entrada; obtém cartas no banco e inicia novas partidas.
  - **`Jogo`**: orquestra a partida (rodadas, regras de escolha de atributo/carta, apuração de vencedor) e aciona persistência de partida/cartas jogadas.
- **`src/main/java/com/supertrunfo/model`**:
  - Entidades de domínio usadas pelo jogo e pela camada de persistência.
- **`src/main/java/com/supertrunfo/dao`**:
  - DAOs com SQL explícito para operações de leitura/escrita no PostgreSQL.
  - `BaseDAO` centraliza criação de conexão.
- **`src/main/java/com/supertrunfo/exceptions`**:
  - Exceções de domínio para validações de consistência no cadastro de cartas.

### Modelos
- **`Carta`**:
  - `id`, `nome`, `forca`, `inteligencia`, `velocidade`
- **`Partida`**:
  - `id`, `data`, `resultado`
  - `roundsVencidosJogador`, `roundsVencidosCPU`, `roundsEmpatados`
  - `forcaUtilizada`, `inteligenciaUtilizada`, `velocidadeUtilizada`
- **`CartaPartida`** (associação):
  - `id`, `doJogador`, `utilizada`
  - `carta`, `partida`

### Qualidade e suporte operacional (visão curricular)
- **Persistência**:
  - PostgreSQL acessado por JDBC com SQL explícito e sequences (`nextval(...)`).
  - Configuração de conexão está embutida em `BaseDAO` (URL/usuário/senha), adequada para contexto educacional/demonstração, mas tipicamente externalizada em aplicações de produção.