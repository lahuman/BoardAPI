package kr.pe.lahuman.board;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by lahuman on 2015-06-23.
 */
@Entity
@Table(name="BOARD")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //작성자
    @Column(length=100, nullable = false)
    @NonNull
    private String author;

    //제목
    @Column(length=1000, nullable = false)
    @NonNull
    private String title;

    //본문
    @Column(length=4000, nullable = false)
    @NonNull
    private String body;

    @Column(name="create_date", insertable = true, updatable = false)
    private Date createDate;

    @Column(name="update_date", insertable = false, updatable = true)
    private Date updateDate;

    private String status="SUCCESS";

    private String message;

    public Board(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @PrePersist
    protected void setCreate() throws Exception {
        validate();
        createDate = new Date();
    }

    @PreUpdate
    protected void setUpdate() throws Exception {
        validate();
        updateDate = new Date();
    }

    protected void validate() throws Exception {
        if(author == null || author.trim().isEmpty()){
           throw new Exception("[author]은  필수 항목 입니다.");
        }
        if(title == null || title.trim().isEmpty()){
            throw new Exception("[title]은  필수 항목 입니다.");
        }
    }
}
