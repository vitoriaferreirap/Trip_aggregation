# Especificação de Design de Sistema: Projeto Passagens Insight

**Data:** 10/02/2026  
**Autor:** Vitória Ferreira  
**Status:** Estrutura Base Concluída / Módulo de Automação em Implementação

---

## 1. Visão Geral
O **Passagens Insight** é uma solução de monitoramento de preços rodoviários focada na região Sul do Brasil. O sistema coleta dados via Web Scraping, normaliza informações geográficas e mantém um histórico de preços para análise de tendências e geração de alertas de desconto.

## 2. Arquitetura e Organização (Service Layer)
O projeto segue o princípio da **Responsabilidade Única (SRP)**, dividindo a lógica em serviços especializados para facilitar a manutenção e o desacoplamento.

### Estrutura de Serviços:

* **Serviços Principais (Core):**
    * `TripService`: Atua como o orquestrador principal, gerenciando o fluxo de busca do início ao fim.
    * `CityService`: Responsável pela normalização de dados geográficos via integração com a API do IBGE, garantindo que os "Slugs" de busca estejam sempre corretos.
* **Serviços de Lógica e Análise:**
    * `PricingComparisonService`: Executa a lógica de comparação em memória para identificar a melhor oferta imediata.
    * `PriceAlertService`: Camada analítica que utiliza janelas temporais para comparar médias históricas e detectar quedas de preço.
* **Serviços de Dados e Automação:**
    * `PriceSnapshotService`: Gerencia a persistência de "fotos" de preços no banco de dados.
    * `AutomatedSearchService`: Motor de busca autônomo que alimenta a base de dados periodicamente.

---

## 3. Fluxo de Dados (Pipeline)

O sistema opera através de dois gatilhos distintos, garantindo eficiência e volume de dados para análise.

### A. Fluxo On-Demand (Gatilho via Usuário)
Focado na experiência imediata de quem acessa o navegador:
* **Verificação de Duplicidade:** O sistema checa se a rota já foi pesquisada no dia atual para evitar o salvamento de dados repetidos e desnecessários.
* **Processamento:** Se os dados são novos, o sistema realiza o scraping "ao vivo", gera uma foto do preço (snapshot) para o banco e entrega a resposta formatada ao usuário.

### B. Fluxo Scheduled (Gatilho via Automação/Robô)
Focado na construção da base histórica para análises futuras:
* **Execução Programada:** O `Scheduler` (agendador) "acorda" o sistema em horários fixos sem intervenção humana.
* **Abrangência:** O robô percorre automaticamente as rotas das capitais do Sul (Curitiba, Florianópolis e Porto Alegre).
* **Série Temporal:** Esses dados são salvos diariamente, criando uma linha do tempo que permite ao sistema identificar se o preço de hoje é uma oportunidade real em relação à média dos últimos dias.

---

## 3. Conceitos de Engenharia Aplicados
Estes são os pilares técnicos que garantem o profissionalismo e a organização do projeto:

* **Responsabilidade Única (SRP):** Cada componente tem uma função bem definida. O serviço de scraping não conhece o banco de dados, e o serviço de alerta não precisa saber como o scraping foi feito.
* **Desacoplamento via DTOs:** A comunicação entre as camadas é feita por objetos de transferência de dados. Isso permite, por exemplo, trocar o site de origem dos dados ou mudar a estrutura do banco de dados sem precisar alterar a lógica de cálculo de preços.
* **Tratamento de Dados Brutos:** Implementação de lógica para "limpar" o HTML externo, tratando valores nulos e filtrando informações inconsistentes para garantir que apenas dados válidos sejam processados.
* **Idempotência e Integridade:** Lógica que impede o salvamento de buscas idênticas no mesmo dia, o que protege o banco de dados contra poluição de dados e economiza recursos de armazenamento.

---

## 4. Roadmap Tecnológico (Próximos Passos)
* [ ] Implementação de Global Exception Handler para tratamento padronizado de erros de API e Scraping.
* [ ] Evolução da lógica de mapeamento para suporte a viagens com conexão.
* [ ] Refatoração da Injeção de Dependências no componente Scheduler para eliminar instâncias manuais.
* [ ] Implementação de automação e alertas.