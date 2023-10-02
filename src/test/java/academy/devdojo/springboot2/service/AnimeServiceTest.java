package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exceptions.httpRequest.BadRequestException;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimePostRequestBodyDTOCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyDTOCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // indica que queremos utilizar o JUnit com o Spring
class AnimeServiceTest {
    // Utiliza-se quando vamos mockar a classe em sí
    @InjectMocks
    private AnimeService animeService;

    // Utiliza-se para inicializar as classes aninhadas (Composição) da classe injetada a cima
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(this.animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(new PageImpl<Anime>(List.of(AnimeCreator.createValidAnime())));

        BDDMockito.when(this.animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(this.animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(this.animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(this.animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(this.animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }
    @Test
    @DisplayName("listAll returns list of anime inside page object when successful")
    void listAll_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("listAllNonPageable returns list of anime when successful")
    void listAllNonPageable_ReturnsListOfAnime_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animesList = animeService.listAllNonPageable();

        Assertions.assertThat(animesList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animesList.get(0).getName()).isEqualTo(expectedName);
    }
    @Test
    @DisplayName("findByIdOrThrowBadRequestException with any ID because returns the same anime all times, when successful")
    void findByIdOrThrowBadRequestException_WithAnyID_ReturnsTheSameAnimeAllTimes_WhenSuccessful() {

        long expectedID = AnimeCreator.createValidAnime().getId();
        Anime expectedAnime = animeService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(expectedAnime).isNotNull();

        Assertions.assertThat(expectedAnime.getId()).isEqualTo(expectedID);
    }
    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when Anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
        // Para cenários NOT SUCCESS é interessante settar esse mock
        // aqui dentro do próprio método de teste!
        BDDMockito.when(this.animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> this.animeService.findByIdOrThrowBadRequestException(1L))
                .withMessageContaining("Anime Not Found");
    }
    @Test
    @DisplayName("findByName returns the list of animes with contains the same name, when successful")
    void findByName_ReturnsTheListOfAnimeWithContainsTheSameName_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animesListWithContainsTheSameName = animeService.findByName(expectedName);

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
        BDDMockito.when(this.animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animesListEmpty = animeService.findByName("Not Exists this anime");

        Assertions.assertThat(animesListEmpty)
                .isNotNull() // pois é "[]" então != null
                .isEmpty();
    }
    @Test
    @DisplayName("save returns a anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {

        Anime anime = animeService.save(AnimePostRequestBodyDTOCreator.createAnimePostRequestBodyDTO());

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
    @Test
    @DisplayName("delete removes and returns empty body but HttpStatus.NO_CONTENT when successful")
    void delete_RemovesAndReturnsEmptyBody_ButHttpStatusNoContent_WhenSuccessful() {

        Assertions.assertThatCode(()->animeService.delete(1)).doesNotThrowAnyException();
    }
    @Test
    @DisplayName("replace updates and returns empty body but HttpStatus.NO_CONTENT when successful")
    void replace_UpdatesAndReturnsEmptyBody_ButHttpStatusNoContent_WhenSuccessful() {

        Assertions.assertThatCode(()->animeService.replace(AnimePutRequestBodyDTOCreator.createAnimePutRequestBodyDTO())).doesNotThrowAnyException();
    }
}