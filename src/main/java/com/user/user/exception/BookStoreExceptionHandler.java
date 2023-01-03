package com.user.user.exception;

import com.user.user.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class BookStoreExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<ObjectError> errorList= exception.getBindingResult().getAllErrors();
        List<String> errMesg =  errorList.stream()
                .map(objErr -> objErr.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDto responseDTO = new ResponseDto("Exception While processing REST Request",errMesg,null);
        return new ResponseEntity<ResponseDto>(responseDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<ResponseDto> handleEmployeePayrollException(BookStoreException exception){
        ResponseDto responseDTO = new ResponseDto("Exception while processing REST request",exception.getMessage(),null);
        return new ResponseEntity<ResponseDto>(responseDTO,HttpStatus.BAD_REQUEST);
    }
}
