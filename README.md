# Passagens Insight

Plataforma de agregação e análise de preços de passagens rodoviárias na Região Sul do Brasil.

O sistema coleta ofertas de viagens, normaliza as cidades, persiste o histórico de preços e prepara dados para identificar oportunidades de economia. O projeto foi desenvolvido como portfólio para demonstrar desenvolvimento backend em Java, integração com dados externos, persistência relacional, automação e testes unitários.

> **Status:** em desenvolvimento. O backend já executa scraping, persiste dados no PostgreSQL local, evita buscas duplicadas no mesmo dia, identifica o menor preço e possui rotina automatizada de monitoramento. O frontend Next.js está sendo estruturado para consumir a API.

## O que o sistema faz

O Passagens Insight possui dois fluxos principais:

### Busca sob demanda

1. Recebe origem, destino e data.
2. Normaliza as cidades para o formato usado na consulta.
3. Verifica se a mesma rota já foi pesquisada no dia.
4. Executa o scraping quando não há uma busca recente.
5. Extrai horários, empresa, tipo de assento e preço.
6. Salva a busca e os snapshots de preços no PostgreSQL.
7. Identifica a passagem mais barata da coleta.
8. Retorna os resultados para a API.

Quando a rota já foi pesquisada no dia, o sistema recupera os snapshots salvos em vez de executar o scraping novamente.

### Monitoramento automatizado

O Spring Scheduling executa rotinas automáticas para rotas entre Curitiba, Florianópolis e Porto Alegre. A automação:

- percorre as rotas configuradas;
- pesquisa viagens para o dia seguinte;
- armazena novos preços;
- compara médias de períodos;
- imprime um alerta no console quando identifica queda de preço igual ou superior a 15%.

## Regras de negócio

### Implementadas

- Normalização de cidades para buscas.
- Scraping de páginas com conteúdo renderizado por JavaScript.
- Persistência de buscas e snapshots de preços.
- Reutilização da busca do mesmo dia para evitar duplicidade.
- Identificação da menor tarifa da coleta atual.
- Execução automatizada de rotas.
- Detecção de queda percentual entre médias de períodos.

### Em evolução

- Média histórica completa e classificação de uma tarifa como **Oferta Imperdível**.
- Filtros por preço, horário, empresa, tipo de assento e viagens diretas.
- Metas de preço configuradas por usuário.
- Persistência de alertas e envio de notificações.
- Integração completa do frontend com os endpoints do backend.

## Arquitetura

O projeto utiliza uma arquitetura em camadas, separando entrada HTTP, regras de negócio, integrações externas e persistência.

```text
Frontend Next.js
        |
        v
Spring Boot REST API
        |
        +--> Controllers
        |       +--> /trips/search
        |       +--> /cities/autocomplete
        |
        +--> Services
        |       +--> TripService
        |       +--> CityService
        |       +--> PricingComparisonService
        |       +--> PriceAlertService
        |       +--> AutomatedSearchService
        |
        +--> Integrações
        |       +--> Playwright
        |       +--> Jsoup
        |       +--> API do IBGE
        |
        +--> Spring Data JPA
                |
                v
          PostgreSQL
```

### Principais componentes

- `TripController`: expõe a busca de viagens.
- `CityController`: expõe o autocomplete de cidades.
- `TripService`: orquestra a busca, a verificação de duplicidade e a persistência.
- `CityService`: normaliza cidades e estados.
- `DeOnibusScrapingClient`: utiliza Playwright e Jsoup para coletar as ofertas.
- `PriceSnapshotService`: salva e recupera snapshots de preços.
- `PricingComparisonService`: identifica a menor tarifa da lista.
- `PriceAlertService`: compara médias de períodos e detecta quedas.
- `AutomatedSearchService`: coordena as rotas automáticas.
- `AutomatedSearchScheduler`: agenda a execução do monitoramento.

## Modelo de dados

O backend utiliza duas entidades principais:

- `TripSearch`: representa uma pesquisa de origem, destino, data da viagem e momento da busca.
- `PriceSnapshot`: representa uma oferta coletada e vinculada à pesquisa que a originou.

O relacionamento é:

```text
TripSearch 1 ---- N PriceSnapshot
```

Esse modelo permite manter o histórico de coletas e comparar preços ao longo do tempo.

## Tecnologias

### Backend

- Java 17
- Spring Boot 3.2.1
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- JUnit 5
- Mockito

### Coleta e integrações

- Playwright 1.49.0 para páginas renderizadas por JavaScript.
- Jsoup 1.17.2 para parsing do HTML.
- API do IBGE para normalização e autocomplete de cidades.

### Frontend

- Next.js 16
- React 19
- TypeScript
- ESLint
- Tailwind CSS

## Estrutura do projeto

```text
trip_aggregation/
├── beckend/
│   ├── pom.xml
│   ├── docker-compose.yaml
│   └── src/
│       ├── main/java/com/vitoriaferreira/trip_aggregator/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── integration/
│       │   ├── repository/
│       │   ├── scheduler/
│       │   └── service/
│       ├── main/resources/
│       │   ├── application.properties
│       │   └── application-local.properties
│       └── test/java/
└── frontend/
    ├── app/
    ├── public/
    ├── package.json
    └── package-lock.json
```

> O diretório `beckend` mantém esse nome porque faz parte da estrutura atual do repositório.

## Pré-requisitos

- JDK 17.
- Maven instalado e disponível no PATH.
- PostgreSQL local executando na porta `5432`.
- Node.js e npm para executar o frontend.
- Chromium instalado pelo Playwright para executar o scraping.

## Configuração do PostgreSQL local

O arquivo `beckend/src/main/resources/application-local.properties` contém a configuração local e está listado no `.gitignore` para não enviar a senha ao GitHub.

Exemplo local:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_LOCAL
```

O arquivo `application.properties` versionado usa variáveis de ambiente:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/postgres}
spring.datasource.username=${DB_USER:postgres}
spring.datasource.password=${DB_PASSWORD}
```

Não faça `git add -f` no arquivo `application-local.properties`.

### PostgreSQL com Docker (opcional)

O arquivo `beckend/docker-compose.yaml` possui uma configuração alternativa para iniciar um PostgreSQL isolado:

```bat
cd C:\caminho\para\trip_aggregation\beckend
docker compose up -d postgres
```

Se usar essa opção, confira se o banco, usuário, porta e senha do compose correspondem ao arquivo de configuração local antes de iniciar o Spring Boot.

## Executando o backend

Na raiz do repositório:

```bat
cd C:\Users\vitoriaf\Documents\PROJETOS2026\trip_aggregation
mvn -f beckend\pom.xml spring-boot:run -Dspring-boot.run.profiles=local
```

Ou dentro da pasta do backend:

```bat
cd C:\Users\vitoriaf\Documents\PROJETOS2026\trip_aggregation\beckend
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

O backend ficará disponível em:

```text
http://localhost:8080
```

No log, uma conexão bem-sucedida normalmente apresenta:

```text
HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection
HikariPool-1 - Start completed.
Tomcat started on port 8080
Started TripAggregatorApplication
```

## Endpoints atuais

### Buscar viagens

```text
GET http://localhost:8080/trips/search?origin=Curitiba&destination=Florianópolis&date=2026-12-20
```

### Autocomplete de cidades

```text
GET http://localhost:8080/cities/autocomplete?palavra=Curitiba
```

As respostas são JSON e podem ser consumidas pelo frontend Next.js.

## Executando os testes

Os testes unitários do `TripService` usam JUnit e Mockito. Eles simulam o repositório, o scraping e os serviços auxiliares; portanto, não acessam o banco nem a internet.

Na pasta `beckend`:

```bat
mvn -Dtest=TripServiceTest test
```

Para executar todos os testes:

```bat
mvn test
```

Os cenários atuais verificam:

- nova busca: scraping, persistência e comparação de preço;
- busca já existente: recuperação do histórico sem novo scraping.

## Executando o frontend

Na pasta do projeto Next.js:

```bat
cd C:\Users\vitoriaf\Documents\PROJETOS2026\trip_aggregation\frontend
npm install
npm run dev
```

Abra:

```text
http://localhost:3000
```

Para validar o frontend:

```bat
npm run lint
npm run build
```

Quando a integração com o backend for implementada, a URL da API poderá ser configurada em `.env.local`:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080
```

## Destaques para portfólio e estágio

Este projeto demonstra competências relevantes para uma vaga de estágio ou início de carreira:

- desenvolvimento de uma API REST com Spring Boot;
- organização em camadas e separação de responsabilidades;
- modelagem relacional com JPA e PostgreSQL;
- integração com fonte externa sem API pública;
- automação de navegador com Playwright;
- parsing e normalização de dados com Jsoup;
- tarefas agendadas com Spring Scheduling;
- prevenção de buscas duplicadas;
- criação de testes unitários com JUnit e Mockito;
- preparação de uma aplicação frontend com Next.js, React e TypeScript;
- cuidado com configuração local e proteção de credenciais.

## Próximos passos

1. Conectar o frontend aos endpoints de viagens e autocomplete.
2. Criar filtros de preço, horário, empresa e tipo de assento.
3. Implementar média histórica e o rótulo `Oferta Imperdível`.
4. Criar entidades e endpoints para metas de preço por usuário.
5. Persistir alertas e adicionar uma estratégia de notificação.
6. Adicionar tratamento global de exceções na API.
7. Criar testes de controller e testes de integração com PostgreSQL.
8. Adicionar documentação OpenAPI/Swagger.
9. Configurar pipeline de CI para build e testes.

## Documentação complementar

- [System Design](SYSTEM_DESIGN.md)
- [Diagrama de design](desingSystem.png)

## Aviso ético

Este projeto possui fins educacionais. O scraping deve respeitar os termos de uso, a disponibilidade e a carga dos sites consultados. O sistema utiliza intervalos entre requisições e deve ser evoluído com responsabilidade.
