package kr.pe.lahuman.board;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by lahuman on 2015-09-21.
 */
public class BoardDto {
    @Data
    public static class Create {
        @NotBlank
        @Size(min=2)
        private String author;
        @NotBlank
        @Size(min=2)
        private String passwd;
        @NotBlank
        @Size(min = 3)
        private String title;
        @NotBlank
        @Size(min = 20)
        private String body;
    }

    @Data
    public static class Response {
        @NotBlank
        @Size(min=2)
        private String author;
        @NotBlank
        @Size(min = 3)
        private String title;
        @NotBlank
        @Size(min = 20)
        private String body;
        private Date createDate;
        private Date updateDate;
    }

    @Data
    public static class Update {
        @NotBlank
        @Size(min=2)
        private String passwd;
        @NotBlank
        @Size(min = 3)
        private String title;
        @NotBlank
        @Size(min = 20)
        private String body;
    }

    @Data
    public static class Delete{
        @NotBlank
        @Size(min=2)
        private String passwd;
    }
}
