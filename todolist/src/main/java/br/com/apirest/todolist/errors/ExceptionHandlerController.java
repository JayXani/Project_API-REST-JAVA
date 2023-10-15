package br.com.apirest.todolist.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// Toda excessão gerada na aplicação vai passar por essa classe
// E Se o erro que ocorrer for o mesmo que HttpMessageNotReadableException, ele vai retornar a mensagem
// Que está no "throws" do taskModel.
public class ExceptionHandlerController {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.status(401).body(e.getMostSpecificCause().getMessage());
    }
}
