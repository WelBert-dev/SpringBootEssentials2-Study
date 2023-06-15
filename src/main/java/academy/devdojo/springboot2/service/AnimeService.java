package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.exceptions.httpRequest.BadRequestException;
import academy.devdojo.springboot2.mapper.AnimeMapper;
import academy.devdojo.springboot2.repository.AnimeRepository;
import academy.devdojo.springboot2.requests.anime.AnimePostRequestBodyDTO;
import academy.devdojo.springboot2.requests.anime.AnimePutRequestBodyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;
    public Page<Anime> listAll(Pageable pageable) {
        return this.animeRepository.findAll(pageable);
    }
    public List<Anime> findByName(String name) {
        return this.animeRepository.findByName(name);
    }
    public Anime findByIdOrThrowBadRequestException(long id) {
        return this.animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime Not Found"));
    }
    @Transactional
    public Anime save(AnimePostRequestBodyDTO animePostDTO) {
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostDTO);
        System.out.println("\n\n\n\n\n APOS MAPPER: \n\n\n\n"+anime);
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
