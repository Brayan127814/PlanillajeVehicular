package planillaje.Vehicular.planillaje.vehicular.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import planillaje.Vehicular.planillaje.vehicular.dtos.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptions {

    //Recurso no encontrado
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> NotFoudExceptionsHandler(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    //Parametros invalidos
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandle(BadRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errores);

    }

    //ACCESO DENEGADO
    public  ResponseEntity<ErrorResponse> ForbidenHandler(ForbiddenException e){
         ErrorResponse error = new ErrorResponse(e.getMessage(),HttpStatus.FORBIDDEN.value());
         return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
<<<<<<< HEAD

=======
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> forbiddenHandler(AccessDeniedException e){
        ErrorResponse error = new ErrorResponse(
                "No tienes permisos para realizar esta acción",
                HttpStatus.FORBIDDEN.value()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
>>>>>>> a77caa99c03789f750b658b4ace1116191233a97
}
