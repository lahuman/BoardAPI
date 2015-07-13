package kr.pe.lahuman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lahuman on 2015-06-24.
 */
@RestController
@ControllerAdvice
public class IndexController implements ErrorController{
    private static final String PATH = "/error";


    @RequestMapping(value = PATH)
    public Map<String, String> error() {
        return getErrorMap("잘못된 접근입니다.");
    }


    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseBody
    public Map<String, String> defaultErrorHandler(HttpServletRequest request, Exception e) {
        return getErrorMap(e.getMessage());
    }

    private Map<String, String> getErrorMap(String message2) {
        Map<String, String> errorMsg = new HashMap<>();
        errorMsg.put("status", "ERROR");
        errorMsg.put("message", message2);
        return errorMsg;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
