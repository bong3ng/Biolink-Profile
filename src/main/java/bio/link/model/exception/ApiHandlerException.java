package bio.link.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import bio.link.model.response.ResponseData;

@RestControllerAdvice
public class ApiHandlerException {
	
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

}
