package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.anime.AnimePostRequestBodyDTO;
import academy.devdojo.springboot2.requests.anime.AnimePutRequestBodyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    public List<Anime> listAll() {
        return this.animeRepository.findAll();
    }
    public List<Anime> findByName(String name) {
        return this.animeRepository.findByName(name);
    }
    public Anime findByIdOrThrowBadRequestException(long id) {
        return this.animeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                                "Anime Not Found"));
    }
    public Anime save(AnimePostRequestBodyDTO animePostDTO) {
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostDTO);
        System.out.println(anime);
        return this.animeRepository.save(anime);
    }
    public void delete(long id) {
        this.animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }
    public void replace(AnimePutRequestBodyDTO animePutDTO) {
        findByIdOrThrowBadRequestException(animePutDTO.getId());

        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutDTO);
        System.out.println(anime);
        this.animeRepository.save(anime);
    }
}
