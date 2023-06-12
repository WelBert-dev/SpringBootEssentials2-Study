package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.requests.anime.AnimePostRequestBodyDTO;
import academy.devdojo.springboot2.requests.anime.AnimePutRequestBodyDTO;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.DateUtil;
import academy.devdojo.springboot2.domain.Anime;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<Anime>> list() {
        log.info("\n\n\nDATA ATUAL FORMATADA: "+ dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK);
        // return ResponseEntity.ok(animeService.listAll());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
       return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBodyDTO animeDTO) {
        System.out.println("\n\n\n\n"+animeDTO);
        return new ResponseEntity<>(animeService.save(animeDTO), HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid AnimePutRequestBodyDTO animeDTO) {
        animeService.replace(animeDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
