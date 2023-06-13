package academy.devdojo.springboot2.exceptions.httpRequest;

import academy.devdojo.springboot2.exceptions.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
@Getter
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails {
    private String camposDoRetornoPersonalizado;
}
