package kr.pe.lahuman.board;

/**
 * Created by lahuman on 2015-09-21.
 */
public class BoardException {
    public static class BoardNotFoundException extends RuntimeException{
        Long id;
        public BoardNotFoundException(Long id){
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

    public static class BoardWrongPasswordException extends RuntimeException {
        Long id;
        public BoardWrongPasswordException(Long id) {
            this.id = id;
        }
        public Long getId() {
            return id;
        }
    }
}
