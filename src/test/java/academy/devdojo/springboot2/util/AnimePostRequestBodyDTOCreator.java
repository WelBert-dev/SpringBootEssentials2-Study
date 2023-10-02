package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.anime.AnimePostRequestBodyDTO;

public class AnimePostRequestBodyDTOCreator {
    public static AnimePostRequestBodyDTO createAnimePostRequestBodyDTO() {
        return AnimePostRequestBodyDTO.builder()
                .name(AnimeCreator.createAnimeToBeSaved().getName())
                .build();
    }
}
