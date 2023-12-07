package HighScore.CapStoneProject.exceptions;

import HighScore.CapStoneProject.payload.ErrorsResponseDTO;
import HighScore.CapStoneProject.payload.ErrorsResponseWithListDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponseWithListDTO handleBadRequest(BadRequestException e) {
        List<String> errorsList;

        if (e.getErrorList() != null) {

            errorsList = e.getErrorList().stream().map(ObjectError::getDefaultMessage).toList();
        } else {

            errorsList = new ArrayList<>();
            if (e.getMessage() != null) {
                errorsList.add(e.getMessage());
            }
        }

        return new ErrorsResponseWithListDTO(errorsList);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsResponseDTO handleUnauthorized(UnauthorizedException e) {
        return new ErrorsResponseDTO(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsResponseDTO handleAccessDenied(AccessDeniedException e) {
        return new ErrorsResponseDTO(e.getMessage());
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResponseDTO handleNotFound(NotFoundException e) {
        return new ErrorsResponseDTO(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsPayload handleJsonError() {
        return new ErrorsPayload("Errore nel formato json , assicurati che ci siano gli amici in ogni proprieta' e che le virgole siano presenti.", new Date());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResponseDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorsResponseDTO("Problema lato server");
    }


}
