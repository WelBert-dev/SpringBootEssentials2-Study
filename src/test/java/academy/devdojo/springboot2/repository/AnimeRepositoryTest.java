package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayName("Tests for Anime Repository (Unit Tests)")
class AnimeRepositoryTest {
    @Autowired // Autowired NÃO é uma boa prática em contextos normais, mas em tests ta tranks
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save creates anime when Successful")
    public void whenCreate_thenPersistData () {

        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }
    private Anime createAnime() {
        return Anime.builder()
                .name("One Piece Live Action 2023")
                .build();
    }
}