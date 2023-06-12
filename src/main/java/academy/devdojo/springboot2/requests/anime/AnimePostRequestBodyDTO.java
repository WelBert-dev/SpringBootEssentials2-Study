package academy.devdojo.springboot2.requests.anime;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AnimePostRequestBodyDTO {
    @NotEmpty(message = "The anime name cannot be empty!")
    private String name;
}
