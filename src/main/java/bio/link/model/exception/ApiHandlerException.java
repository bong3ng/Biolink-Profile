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
//	@ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    public Status handleAllException(Exception ex, WebRequest request) {
//        // quá trình kiểm soat lỗi diễn ra ở đây
//        return new Status(0, "Sai thông tin đăng nhập");
//    }
//	
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseData notFoundException(NotFoundException ex) {

        return new ResponseData(false, ex.getMessage() , null);
    }
    
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.FORBIDDEN)
//    public ResponseData notFound(Exception ex, WebRequest request) {
////    	String message;
////    	if(userRepository.find)
//    	return new ResponseData(false, ex.getMessage(), null);
//    }

}
