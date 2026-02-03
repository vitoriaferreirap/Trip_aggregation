# Sistema de Comparação de Passagens de Ônibus

Este projeto tem como **objetivo principal o aprendizado de web scraping**, utilizando Java, Jsoup e uma arquitetura em camadas.

Projeto **com proposito educacional**  é importante ressaltar que web scraping deve ser feito com responsabilidade ética.

Embora o sistema possua características de um produto real (API REST, services, DTOs e integração com dados oficiais), o foco está em **entender como projetar, implementar e manter scrapers**, considerando que **cada site possui HTML, regras e limitações próprias**.

A aplicação irá evoluir para um sistema de comparação de passagens de ônibus, agregando preços e horários de **três plataformas diferentes**.  
Neste primeiro momento, apenas uma plataforma está integrada.

---

## Visão Geral da Arquitetura

O sistema segue uma arquitetura em camadas, separando responsabilidades para evitar acoplamento entre scraping, regras de negócio e exposição da API.
Essa separação é essencial porque **scraping é volátil**: mudanças no HTML não devem impactar controllers ou DTOs.

---

## Fluxo Funcional da Busca de Viagens

1. Usuário escolhe cidade de origem e destino.
2. Autocomplete retorna `CityResponse` (nome da cidade + UF) API IBGE.
3. A aplicação gera os slugs (`toSlug`) a partir do nome e estado.
4. Usuário informa a data da viagem (`LocalDate`).
5. O service chama `searchTrips(originSlug, destinationSlug, date)`.
6. A URL é montada e o HTML é baixado.
7. Jsoup percorre o DOM e extrai horários, preços e destinos.
8. Dados são normalizados e retornados ao frontend.

---

## Padrão de URL Utilizado no Scraping

```
https://rodoviariacuritiba.com.br/passagens-de-onibus/{originSlug}-para-{destinationSlug}-todos?departureDate={dd/MM/yyyy}
```

---

## Controllers e Casos de Uso

| Controller / Service | Função |
|---------------------|--------|
| **CityController / CityService** | Autocomplete via IBGE. Sem scraping. |
| **TripController / TripService** | Busca real de viagens via scraping. |

---

## Estratégias de Extração com Jsoup

### Abordagem 1 — `select().text()` (HTML estruturado)

- Usada quando o HTML possui marcação confiável.
- `select()` navega pelo DOM.
- `.text()` retorna o valor em texto do elemento HTML.

---

### Abordagem 2 — `.text()` + Regex (HTML mal estruturado)

- Usada quando o HTML não é confiável.
- Extrai texto bruto do bloco.
- Aplica Regex e regras manuais.

---

## Dificuldades Encontradas

- Acesso correto aos dados de UF da API (IBGE).
- Extração em páginas sem identificadores confiáveis.
- Cada site exige uma estratégia de scraping diferente.

---

## Considerações Finais

O foco do projeto é o **aprendizado do processo de scraping**:

- Análise de HTML real
- Arquitetura preparada para múltiplos scrapers
- Separação clara de responsabilidades
- Registro de decisões técnicas
