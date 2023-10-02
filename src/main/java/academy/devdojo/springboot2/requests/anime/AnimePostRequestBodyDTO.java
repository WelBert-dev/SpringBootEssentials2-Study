package academy.devdojo.springboot2.requests.anime;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AnimePostRequestBodyDTO {
    @NotEmpty(message = "The anime name cannot be empty!")
    private String name;
}
