package kr.pe.lahuman.board;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

/**
 * Created by lahuman on 2015-09-21.
 */
@Service
@Slf4j
public class BoardService {

    @Autowired
    private BoardRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Board createBoard(BoardDto.Create dto){
        Board board = modelMapper.map(dto, Board.class);

        return repository.save(board);
    }

    public Board getBoard(Long id) {
        Board board = repository.findOne(id);
        if(board == null){
            throw new BoardException.BoardNotFoundException(id);
        }
        return board;
    }

    public Board update(Long id, BoardDto.Update updateDto) {
        Board board = getBoard(id);
        if(!board.getPasswd().equals(updateDto.getPasswd())){
            throw new BoardException.BoardWrongPasswordException(id);
        }
        board.setTitle(updateDto.getTitle());
        board.setBody(updateDto.getBody());
        return repository.save(board);
    }

    public void delete(Long id, BoardDto.Delete deleteDto) {
        Board board = getBoard(id);
        if(!board.getPasswd().equals(deleteDto.getPasswd())){
            throw new BoardException.BoardWrongPasswordException(id);
        }
        repository.delete(getBoard(id));
    }

    public Page<Board> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
