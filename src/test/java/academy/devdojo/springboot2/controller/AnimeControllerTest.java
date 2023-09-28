package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.AnimeCreator;
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
        PageImpl<Anime> animePage = new PageImpl<Anime>(List.of(AnimeCreator.createValidAnime()));

        // Quando alguem chamas o listAll no service, retorna o animePage
        // alimenta para este: list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful()
        BDDMockito.when(this.animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        // Quando alguem chama o listAllNonPageable no service, retorna List<Anime>
        // alimenta para este: listAllNonPageable_ReturnsListOfAnime_WhenSuccessful()
        BDDMockito.when(this.animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));
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
}