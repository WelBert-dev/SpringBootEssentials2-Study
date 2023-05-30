package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// COM Contexto anime:
@RestController()
@RequestMapping("anime")
public class AnimeController {

    // COM contexto:
    //localhost:8080/anime/list
    // Defasado/obsoleto:
//    @RequestMapping(method = RequestMethod.GET, path = "list")
    // Novo jeito elegante:
    @GetMapping(path="list")
    public List<Anime> list() {
        return List.of(new Anime("DBZ"),
                       new Anime("Berserk"));
    }
}
