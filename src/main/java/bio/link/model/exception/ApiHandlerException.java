package bio.link.model.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bio.link.model.response.ResponseData;
import bio.link.repository.UserRepository;

@RestControllerAdvice
public class ApiHandlerException {
	@Autowired
	UserRepository userRepository;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseData notFoundException(NotFoundException ex) {

        return new ResponseData(false, ex.getMessage() , null);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData notFound(Exception ex) {

    	return new ResponseData(false, "Đã xảy ra lỗi phía backend", null);
    }

}
