package academy.devdojo.springboot2.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import academy.devdojo.springboot2.domain.Anime;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Log4j2
public class SpringClient {

    public static void execute() {

        ResponseEntity<Anime> responseEntity = new org.springframework.web.client.RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 4);

        System.out.println(responseEntity);

        // AnimeEntity é o tipo do Objeto JSON que será retornado pela API e o prórpio JACKSON vai mapear por nós.
        // As vantagens do retorno ser encapsulado em um ResponseEntity<> é que ele armazena mais informações sobre a requisição
        // Como por exemplo o código HTTP, assim podemos utilizar if's para verificar se deu tudo OK (200)

        // As diferenças ente `.getForEntity` e `.getForObject` é apenas o tipo de Retorno!
//    AnimeEntity responseObject = new org.springframework.web.client.RestTemplate()
//            .getForObject("https://localhost:8080/api/animes/2", AnimeEntity.class);

    }
    public static void executeHttpGetWithReturnsListAll_array() {
        Anime[] animes_array = new RestTemplate()
                .getForObject("http://localhost:8080/animes/listAllNonPageable",
                               Anime[].class);

        log.info(Arrays.toString(animes_array));
    }
    public static void executeHttpGetWithReturnsListAll_ListOrOtherCollection() {

        ResponseEntity<List<Anime>> animes_list = new RestTemplate()
                .exchange("http://localhost:8080/animes/listAllNonPageable",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Anime>>() {
                        });

        log.info(animes_list.getBody());
    }
    public static void executeHttpPostWithReturnsObject() {
        // Cria o Objeto que será enviado no Body da requisição para salvar utilizando a API:
        Anime kingdom = Anime.builder()
                .name("Kingdom")
                .build();

        Anime responseOfAnimeCreated_object = new RestTemplate()
                .postForObject("http://localhost:8080/animes",
                        kingdom,
                        Anime.class);

        log.info("Saved anime response of Created: {}", responseOfAnimeCreated_object);
    }
    public static void executeHttpPostWithReturnsResponseEntityWrapperOfAnime() {
        // Cria o Objeto que será enviado no Body da requisição para salvar utilizando a API:
        Anime onePiece = Anime.builder()
                .name("One Piece Live Action")
                .build();

        ResponseEntity<Anime> responseOfAnimeCreated_wrapperResponseEntityOfAnime = new RestTemplate()
                .postForEntity("http://localhost:8080/animes",
                        onePiece,
                        Anime.class);

        log.info(responseOfAnimeCreated_wrapperResponseEntityOfAnime.getBody());
    }
    public static void executeHttpPostWithExchangeBecauseMorePersonalization() {

        // Cria o Objeto que será enviado no Body da requisição para salvar utilizando a API:
        Anime jujutsuNoKaisen = Anime.builder()
                .name("jujutsu no Kaisen")
                .build();

        ResponseEntity<Anime> responseOfAnimeCreated_exchange = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                        HttpMethod.POST,
                        new HttpEntity<>(jujutsuNoKaisen),
                        Anime.class);

        log.info(responseOfAnimeCreated_exchange.getBody());
    }
    public static void executeHttpPostWithExchangeBecauseMorePersonalization_withHttpHeaders() {

        // Cria o Objeto que será enviado no Body da requisição para salvar utilizando a API:
        Anime kimetsuNoYaiba = Anime.builder()
                .name("Kimetsu no Yaiba")
                .build();

        ResponseEntity<Anime> responseOfAnimeCreated_exchange_withHttpHeaders = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                        HttpMethod.POST,
                        new HttpEntity<>(kimetsuNoYaiba, createRequestHeaders_withMimeTypeApplicationJson()),
                        Anime.class);

        log.info(responseOfAnimeCreated_exchange_withHttpHeaders.getBody());
    }
    public static HttpHeaders createRequestHeaders_withMimeTypeApplicationJson() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }
    public static void executeHttpPutWithExchange_BecauseReturnsHttpStatus() {
        // Utilizamos exchange pois podemos personalizar o retorno e retornar
        // o HTTP STATUS, pois o `new RestTemplate().put()` ou `.delete()` retorna VOID!

        // Pega o anime que será modificado fazendo requisição get:
        ResponseEntity<Anime> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 4);

        log.info("Anime de ID:4 retornado pelo GET: {}", responseEntity.getBody());

        // Monta o objeto anime que será enviado para o PUT:
        Anime animeId4 = Anime.builder()
                .id(responseEntity.getBody().getId())
                .name(responseEntity.getBody().getName() + " Updated")
                .build();

        // Finalmente faz a alteração batendo na propria API em execução com o HTTP PUT
        ResponseEntity<Void> responseOfAnimeUpdated_exchange = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                        HttpMethod.PUT,
                        new HttpEntity<>(animeId4, createRequestHeaders_withMimeTypeApplicationJson()),
                        Void.class);

        log.info(responseOfAnimeUpdated_exchange.getBody());
    }
    public static void executeHttpPut_NonReturnsHttpStatus() {
        // Sem o uso do exchange() que possibilita maiores configurações na requisição,
        // por conta disto o retorno deste .put() NÃO TEM RETORNO, ou seja,
        // NÃO é possível retornar o Status Code HTTP

        // Pega o anime que será modificado fazendo requisição get:
        ResponseEntity<Anime> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 4);

        log.info("Anime de ID:4 retornado pelo GET: {}", responseEntity.getBody());

        // Altera nome do anime para saber que modificou, já que não tem retornos:
        Objects.requireNonNull(responseEntity.getBody())
                .setName(responseEntity.getBody().getName() + "UPDATED NON RETURNS");

        // Finalmente faz a alteração batendo na propria API em execução com o HTTP PUT

        new RestTemplate()
                .put("http://localhost:8080/animes/",
                        new HttpEntity<>(responseEntity.getBody(),
                        createRequestHeaders_withMimeTypeApplicationJson()));
    }
    public static void executeHttpDeleteWithExchange_BecauseReturnsHttpStatus() {
        // Utilizamos exchange pois podemos personalizar o retorno e retornar
        // o HTTP STATUS, pois o `new RestTemplate().put()` ou `.delete()` retorna VOID!

        // Pega o anime que será DELETADO, fazendo requisição get:
        ResponseEntity<Anime> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 4);

        log.info("Anime de ID:4 retornado pelo GET que sera DELETADO: {}", responseEntity.getBody());

        // Finalmente faz a alteração batendo na propria API em execução com o HTTP PUT
        ResponseEntity<Void> responseOfAnimeDeleted_exchange = new RestTemplate()
                .exchange("http://localhost:8080/animes/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        responseEntity.getBody().getId());

        log.info(responseOfAnimeDeleted_exchange.getBody());
    }
    public static void executeHttpDelete_NonReturnsHttpStatus() {
        // Sem o uso do exchange() que possibilita maiores configurações na requisição,
        // por conta disto o retorno deste .delete() NÃO TEM RETORNO, ou seja,
        // NÃO é possível retornar o Status Code HTTP

        // Pega o anime que será deletado, fazendo requisição get:
        ResponseEntity<Anime> responseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 5);

        log.info("Anime de ID:5 retornado pelo GET: {}", responseEntity.getBody());

        // Finalmente faz a deleção batendo na propria API em execução com o HTTP DELETE

        new RestTemplate()
                .delete("http://localhost:8080/animes/{id}",
                        responseEntity.getBody().getId());
    }
}
