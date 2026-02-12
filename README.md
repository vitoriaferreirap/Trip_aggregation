# Passagens Insight 

O **Passagens Insight** é uma plataforma inteligente de monitoramento e análise de preços de passagens de ônibus. O sistema transforma dados brutos extraídos via Web Scraping em um histórico estruturado, permitindo identificar oportunidades de economia através de inteligência de dados.

> **Status do Projeto:** Em desenvolvimento (Fase de implementação da Automação e Alertas).

### Automação & Resiliência
Implementei uma solução de scraping resiliente utilizando **Playwright** para superar desafios de renderização dinâmica (JavaScript) e mitigar bloqueios de IP, aplicando técnicas de rotação de URLs e emulação de comportamento humano. Isso garante a extração de dados mesmo em interfaces modernas e protegidas.o.

---

## Objetivo do Projeto
Este projeto foi idealizado para aplicar conceitos avançados de **Desenvolvimento Java/Spring**, focando em:
* **Web Scraping:** Extração de dados de fontes sem APIs públicas.
* **Arquitetura em Camadas:** Organização de código focada em manutenção e escalabilidade.
* **Tratamento de Dados:** Normalização de informações desestruturadas para persistência em bancos relacionais.

## Tecnologias Utilizadas
* **Java 17 & Spring Boot 3** - Base robusta para o desenvolvimento do ecossistema.
* **Playwright** - Automação de navegador para renderização de JavaScript e bypass de proteções.
* **Jsoup** - Parsing eficiente de dados HTML estruturados.
* **Spring Data JPA & PostgreSQL** - Modelagem relacional e persistência de dados.
* **Spring Scheduling** - Agendamento de tarefas para monitoramento contínuo.
* **API do IBGE** - Padronização e normalização de nomes de cidades e estados.

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