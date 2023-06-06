package academy.devdojo.springboot2.requests.anime;

import lombok.Data;

@Data
public class AnimePutRequestBodyDTO {
    private Long id;
    private String name;
}
