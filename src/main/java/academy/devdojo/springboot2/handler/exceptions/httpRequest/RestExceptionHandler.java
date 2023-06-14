package academy.devdojo.springboot2.handler.exceptions.httpRequest;

import academy.devdojo.springboot2.exceptions.ExceptionDetails;
import academy.devdojo.springboot2.exceptions.ValidationExceptionDetails;
import academy.devdojo.springboot2.exceptions.httpRequest.BadRequestException;
import academy.devdojo.springboot2.exceptions.httpRequest.BadRequestExceptionDetails;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handleBadRequestException(
            BadRequestException exception) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Check the documentation!")
                        .details(exception.getMessage())
                        .developerMessage(exception.getClass().getName())
                        .camposDoRetornoPersonalizado("HANDLER PERSONALIZADOOO! ;D").build()
                , HttpStatus.BAD_REQUEST
        );
    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ValidationExceptionDetails> handleMethodArgumentNotValidException(
//            MethodArgumentNotValidException exception) {
//        log.info("Fields {}", exception.getBindingResult().getFieldError().getField());
//
//        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//
//        String fields_str = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
//        String fieldsMessage_str = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
//
//        return new ResponseEntity<>(
//                ValidationExceptionDetails.builder()
//                        .timestamp(LocalDateTime.now())
//                        .status(HttpStatus.BAD_REQUEST.value())
//                        .title("Bad Request Exception, Invalid Fields!")
//                        .details("Check the field(s) error")
//                        .developerMessage(exception.getClass().getName())
//                        .fields(fields_str)
//                        .fieldsMessage(fieldsMessage_str).build()
//                , HttpStatus.BAD_REQUEST
//        );
//    }
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("Fields {}", ex.getBindingResult().getFieldError().getField());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        String fields_str = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage_str = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception, Invalid Fields!")
                        .details("Check the field(s) error")
                        .developerMessage(ex.getClass().getName())
                        .fields(fields_str)
                        .fieldsMessage(fieldsMessage_str).build()
                , HttpStatus.BAD_REQUEST
        );
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .title(ex.getCause().getMessage())
                .details(ex.getMessage())
                .developerMessage(ex.getClass().getName()).build();

        return new ResponseEntity<>(exceptionDetails, headers, status);
    }
}
