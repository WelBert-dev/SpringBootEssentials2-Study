package academy.devdojo.springboot2.handler.exceptions.httpRequest;

import academy.devdojo.springboot2.exceptions.httpRequest.BadRequestException;
import academy.devdojo.springboot2.exceptions.httpRequest.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the documentation!")
                        .details(bre.getMessage())
                        .developerMessage(bre.getClass().getName())
                        .camposDoRetornoPersonalizado("HANDLER PERSONALIZADOOO! ;D").build()
                , HttpStatus.BAD_REQUEST
        );
    }
}
