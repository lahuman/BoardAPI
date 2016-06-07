package kr.pe.lahuman.common;

import lombok.Data;

import java.util.List;

/**
 * Created by lahuman on 2015-09-21.
 */
@Data
public class ErrorResponse {
    private String message;
    private String code;
    private List<FieldError> errors;

    @Data
    public static class FieldError {
        private String field;
        private String code;
        private String message;

    }
}
