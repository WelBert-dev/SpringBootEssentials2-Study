package academy.devdojo.springboot2.requests.anime;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AnimePutRequestBodyDTO {
    @NotNull(message = "The anime ID cannot be NULL!")
    private Long id;
    @NotEmpty(message = "The anime name cannot be empty!")
    private String name;
}
