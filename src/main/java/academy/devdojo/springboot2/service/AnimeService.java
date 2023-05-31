package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Anime;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AnimeService {
    private static List<Anime> animesList;
    static {
        animesList = new ArrayList<>(List.of(new Anime(1L, "DBZ"),
                                             new Anime(2L, "Berserk")));
    }
    public List<Anime> listAll() {
        return this.animesList;
    }
    public Anime findById(long id) {
        return animesList.stream()
                .filter(anime -> anime.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                                "Anime Not Found"));
    }
    public Anime save(Anime anime) {
        anime.setId(ThreadLocalRandom.current().nextLong(3, 100000));
        animesList.add(anime);

        return anime;
    }
}
