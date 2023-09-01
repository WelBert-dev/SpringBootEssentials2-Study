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
}
