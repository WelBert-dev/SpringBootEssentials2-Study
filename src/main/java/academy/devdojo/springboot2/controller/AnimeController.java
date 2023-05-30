package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.DateUtil;
import academy.devdojo.springboot2.domain.Anime;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

// COM Contexto anime:
@Log4j2
@RestController()
@RequestMapping("animes")
@RequiredArgsConstructor
public class AnimeController {
//    @Autowired
    private final DateUtil dateUtil;
    private final AnimeService animeService;

//    public AnimeController(DateUtil dateUtil) {
//        this.dateUtil = dateUtil;
//    }

    // COM contexto:
    //localhost:8080/animes/
    // Defasado/obsoleto:
//    @RequestMapping(method = RequestMethod.GET, path = "list")
    // Novo jeito elegante:
    @GetMapping
    public List<Anime> list() {
        log.info("\n\n\nDATA ATUAL FORMATADA: "+ dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return animeService.listAll();
    }
}
