# Passagens Insight 

O **Passagens Insight** é uma plataforma inteligente de monitoramento e análise de preços de passagens de ônibus. O sistema transforma dados brutos extraídos via Web Scraping em um histórico estruturado, permitindo identificar oportunidades de economia através de inteligência de dados.

> **Status do Projeto:** Em desenvolvimento (Fase de implementação da Automação e Alertas).

---

## Objetivo do Projeto
Este projeto foi idealizado para aplicar conceitos avançados de **Desenvolvimento Java/Spring**, focando em:
* **Web Scraping:** Extração de dados de fontes sem APIs públicas.
* **Arquitetura em Camadas:** Organização de código focada em manutenção e escalabilidade.
* **Tratamento de Dados:** Normalização de informações desestruturadas para persistência em bancos relacionais.

## Tecnologias Utilizadas
* **Java 17** & **Spring Boot 3**
* **Spring Data JPA** & **PostgreSQL** (Persistência e Séries Temporais)
* **Jsoup** (Motor de extração HTML)
* **API do IBGE** (Normalização dinâmica de localidades)
* **Spring Scheduling** (Automação de tarefas em segundo plano)

---

## Decisões de Engenharia
Diferente de scripts de busca simples, o Passagens Insight foi projetado seguindo princípios de **Clean Code** e **SOLID**:

* **Separação de Responsabilidades (SRP):** Cada serviço cuida de uma parte do processo (busca, normalização, comparação ou alerta).
* **Desacoplamento:** O sistema utiliza **DTOs** para garantir que a lógica de negócio seja independente da origem dos dados.
* **Idempotência:** Filtros inteligentes que evitam a persistência de buscas duplicadas no mesmo dia, otimizando o armazenamento.



---

## Documentação Detalhada
Para uma visão profunda sobre a arquitetura, os fluxos de dados, e as regras de negócio aplicadas, acesse:
[**Visualizar Documento de System Design**](SYSTEM_DESIGN.md)

---

## Funcionalidades e Roadmap
- [x] **Integração IBGE:** Normalização de cidades e UFs para buscas precisas.
- [x] **Scraping:** Extração resiliente de preços, horários e empresas.
- [x] **Histórico de Preços:** Persistência automática de fotos de preço (Snapshots).
- [x] **Comparação Real-time:** Identificação imediata da melhor oferta para o usuário.
- [ ] **Automação (Scheduler):** Coleta diária de preços nas capitais do Sul (Em progresso).
- [ ] **Monitor de Alertas:** Notificação de queda de preço baseada em média histórica.

---

## Aviso Ético
Este projeto possui fins **estritamente educacionais**. O Web Scraping é realizado de forma responsável, com intervalos de tempo que respeitam a carga dos servidores e visando apenas o estudo de técnicas de extração e análise de dados.