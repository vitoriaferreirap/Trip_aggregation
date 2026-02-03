Cliente (browser/Postman)
        ↓
SUA API (Controller)
        ↓
Service (lógica)
        ↓
Scraper (chama sites externos)
        ↓
Normalização
        ↓
Resposta para o cliente


O fluxo final fica assim:
Usuário escolhe cidade → autocomplete retorna CityResponse.
Você pega o nome da cidade e o estado → gera o slug com toSlug.
Data da viagem → o usuário fornece, converte para LocalDate.
Chama searchTrips(originSlug, destinationSlug, date) → monta a URL e baixa HTML.
Jsoup percorre o DOM → extrai horários, preços e destinos.
Retorna lista de objetos (de preferência BusTrip) para o frontend.


Dificuldades:
Acessar UF do IBGE

| Camada / Classe                | Responsabilidade principal                                                                                     
| **DeOnibusScrapingClient**     | Conectar no site externo, baixar HTML, parsear DOM e retornar **raw results** (Strings ou Map).                                      |
| **TripService**                | Receber os objetos `CityResponse` (nome + estado), gerar slugs, passar para scraping e transformar **raw results em Trip_Response**. |
| **Trip_Response (DTO)**        | Apenas representar os dados de uma viagem (origin, destination, time, price, company). **Não sabe nada de scraping**.                |
| **CityService / CityResponse** | Obter cidades do IBGE, preencher `state`, normalização, autocomplete.                                                                |


padrao URL 
https://rodoviariacuritiba.com.br/passagens-de-onibus/{originSlug}-para-{destinationSlug}-todos?departureDate={dd/MM/yyyy}


| Controller / Service         | Função                                                                                    |
| ---------------------------- | ----------------------------------------------------------------------------------------- |
| CityController / CityService | Autocomplete: retorna lista de cidades + UF do IBGE, sem scraping                         |
| TripController / TripService | Busca de viagens reais: recebe origem, destino e data, chama scraping, retorna resultados |

