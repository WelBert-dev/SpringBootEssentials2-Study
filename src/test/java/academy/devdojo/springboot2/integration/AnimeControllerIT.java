package academy.devdojo.springboot2.integration;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // caso já tenha uma aplicação running na porta padrão
@AutoConfigureTestDatabase // DB IN MEMORY
class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnimeRepository animeRepository;

//    @LocalServerPort // Maneira de pegar a porta se necessário, aqui não será preciso
//    private int portRunning;
    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        // Alimenta o banco para poder retornar valores, lembrando que o banco é in memory
        // e após executar os testes ele é destruído!
        Anime animeToBeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        // Utiliza a Implementação personalizada para o PageImpl<Anime> pois
        // o default da problemas, essa implementação simula o JSON
        // de retorno dela
        String expectedName = animeToBeSaved.getName();
        PageableResponse<Anime> responseBodyOfAnimesListInsideIsPageableObject = testRestTemplate.exchange("/animes", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(responseBodyOfAnimesListInsideIsPageableObject).isNotNull();

        Assertions.assertThat(responseBodyOfAnimesListInsideIsPageableObject.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(responseBodyOfAnimesListInsideIsPageableObject.toList().get(0).getName()).isEqualTo(expectedName);

        animeRepository.delete(animeToBeSaved);
    }
}
