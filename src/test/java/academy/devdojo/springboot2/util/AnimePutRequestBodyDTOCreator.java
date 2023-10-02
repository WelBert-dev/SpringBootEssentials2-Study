package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.requests.anime.AnimePostRequestBodyDTO;
import academy.devdojo.springboot2.requests.anime.AnimePutRequestBodyDTO;

public class AnimePutRequestBodyDTOCreator {
    public static AnimePutRequestBodyDTO createAnimePutRequestBodyDTO() {
        return AnimePutRequestBodyDTO.builder()
                .name(AnimeCreator.createValidUpdatedAnime().getName())
                .id(AnimeCreator.createValidUpdatedAnime().getId())
                .build();
    }
}
