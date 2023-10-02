package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.anime.AnimePostRequestBodyDTO;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyDTOCreator;
import academy.devdojo.springboot2.util.DateUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

//@SpringBootTest // Ele vai inicializar o contexto tôdo, ou seja,
// a aplicação por completo, mas aqui o objetivo é testes UNITÁRIOS
// logo, não é priciso inicializar tudo, apenas o método único em
// questão que desejamos realizar teste deve ser carregado...
// Então vamos utilizar outra abordagem:

@ExtendWith(SpringExtension.class) // indica que queremos utilizar o JUnit com o Spring
class AnimeControllerTest {

    // Utiliza-se quando vamos mockar a classe em sí
    @InjectMocks
    private AnimeController animeController;

    // Utiliza-se para inicializar as classes aninhadas (Composição) da classe injetada a cima
    @Mock
    private AnimeService animeServiceMock;

    // Define o comportamento das classes mockadas, neste caso, o
    // Page<Anime> que o animeService retornaria, devemos simular
    // esse comportamento esperado da classe mockada a cima
    @BeforeEach
    void setUp() {

        // Quando alguem chamas o listAll no service, retorna o animePage
        // alimenta para este: list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful()
        BDDMockito.when(this.animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(new PageImpl<Anime>(List.of(AnimeCreator.createValidAnime())));

        // Quando alguem chama o listAllNonPageable no service, retorna List<Anime>
        // alimenta para este: listAllNonPageable_ReturnsListOfAnime_WhenSuccessful()
        BDDMockito.when(this.animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Quando alguem chama o findByIdOrThrowBadRequestException no service,
        // retorna o mesmo anime de todos os testes, independentemente do ID de argumento
        // alimenta para este: findById_WithAnyID_ReturnsTheSameAnimeAllTimes_WhenSuccessful()
        BDDMockito.when(this.animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        // Quando alguem chama o findByName no service,
        // retorna List<Anime> contendo todas as ocorrências de mesmo name, neste caso contendo apenas um anime
        // alimenta para este: findByName_ReturnsTheListOfAnimeWithContainsTheSameName_WhenSuccessful
        BDDMockito.when(this.animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Quando alguem chama o save no service, e É A ESTRUTURA DE UM ANIME VÁLIDO retorna o anime
        // alimenta para este:
        BDDMockito.when(this.animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBodyDTO.class)))
                .thenReturn(AnimeCreator.createValidAnime());

    }
    @Test
    @DisplayName("List returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("ListAll returns list of anime when successful")
    void listAllNonPageable_ReturnsListOfAnime_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animesList = animeController.listAllNonPageable().getBody();

        Assertions.assertThat(animesList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animesList.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findById with any ID because returns the same anime all times, when successful")
    void findById_WithAnyID_ReturnsTheSameAnimeAllTimes_WhenSuccessful() {

        long expectedID = AnimeCreator.createValidAnime().getId();
        Anime expectedAnime = animeController.findById(1L).getBody();

        Assertions.assertThat(expectedAnime).isNotNull();

        Assertions.assertThat(expectedAnime.getId()).isEqualTo(expectedID);
    }
    @Test
    @DisplayName("findByName returns the list of animes with contains the same name, when successful")
    void findByName_ReturnsTheListOfAnimeWithContainsTheSameName_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animesListWithContainsTheSameName = animeController.findByName(expectedName).getBody();

        Assertions.assertThat(animesListWithContainsTheSameName)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animesListWithContainsTheSameName.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByName returns an empty list, when anime is not found")
    void findByName_ReturnsTheEmptyList_WhenAnimeIsNotFound() {

        // Para cenários Not Successful é interessante definir o comportamento
        // do mock dentro do teste que utiliza aquele cenário especifico:
        BDDMockito.when(this.animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animesListEmpty = animeController.findByName("Not Exists this anime").getBody();

        Assertions.assertThat(animesListEmpty)
                .isNotNull() // pois é "[]" então != null
                .isEmpty();
    }
    @Test
    @DisplayName("save returns a anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {

        Anime anime = animeController.save(AnimePostRequestBodyDTOCreator.createAnimePostRequestBodyDTO()).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
}