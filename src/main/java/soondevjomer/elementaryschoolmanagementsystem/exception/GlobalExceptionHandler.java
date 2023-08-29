package soondevjomer.elementaryschoolmanagementsystem.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoRecordFoundException(NotFoundException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleAlreadyExistsException(AlreadyExistsException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleExpiredJwtException(ExpiredJwtException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(AccessDeniedException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());

        return new ResponseEntity<>(problemDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ProblemDetail> handleJwtException(JwtException exception) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());

        return new ResponseEntity<>(problemDetail, HttpStatus.FORBIDDEN);
    }
}
