package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.anime.AnimePostRequestBodyDTO;
import academy.devdojo.springboot2.requests.anime.AnimePutRequestBodyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // possibilita fazer dependency injection
public abstract class AnimeMapper {
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
    @Mapping(target = "name", source = "animePostRequestBodyDTO.name")
    public abstract Anime toAnime(AnimePostRequestBodyDTO animePostRequestBodyDTO);
    public abstract Anime toAnime(AnimePutRequestBodyDTO animeputRequestBodyDTO);
}
