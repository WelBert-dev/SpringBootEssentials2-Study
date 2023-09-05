package academy.devdojo.springboot2.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import academy.devdojo.springboot2.domain.Anime;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


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
}
