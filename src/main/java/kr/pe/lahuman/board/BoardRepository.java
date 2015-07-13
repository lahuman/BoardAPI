package kr.pe.lahuman.board;



import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lahuman on 2015-06-23.
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
}
