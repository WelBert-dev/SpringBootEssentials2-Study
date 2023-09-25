package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

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
    @Test
    @DisplayName("Save updates anime when Successful")
    public void whenUpdate_thenChangeAndPersistData () {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName(animeSaved.getName() + " Updated");

        Anime animeUpdated = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }
    @Test
    @DisplayName("Delete removes anime when Successful")
    public void whenDelete_thenRemoveData () {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeSaved);

        Optional<Anime> animeDeletedOptional = this.animeRepository.findById(animeSaved.getId());

        Assertions.assertThat(animeDeletedOptional).isEmpty();
    }
    private Anime createAnime() {
        return Anime.builder()
                .name("One Piece Live Action 2023")
                .build();
    }
}