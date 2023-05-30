package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Anime;

import java.util.List;

public interface AnimeRepository {
    public List<Anime> listAll();
}
