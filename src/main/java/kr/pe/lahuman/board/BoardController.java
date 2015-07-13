package kr.pe.lahuman.board;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lahuman on 2015-06-23.
 */
@RestController
@RequestMapping(value="/api/posts")
public class BoardController {

    static final Logger log = LoggerFactory.getLogger(BoardController.class);


    @Autowired
    private BoardRepository boardRepository;

    @ResponseBody
    @RequestMapping( method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Board> list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("call list");
        List<Board> list = boardRepository.findAll();
        list = list==null?new ArrayList<Board>():list;

        if(list.size() == 0){
            throw new Exception("목록이 비었습니다.");
        }
        return list;
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Board view(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Board board = getBoard(id);
        return board;
    }

    private Board getBoard(@PathVariable Long id) throws Exception {
        Board board = boardRepository.findOne(id);

        if(board == null) {
            throw new Exception("ID : ["+id+ "] 결과 없음");
        }
        return board;
    }

    private Board errorBoardMsg(String msg) {
        return new Board("ERROR", msg);
    }

    private Board sucessBoardMsg(String msg) {
        return new Board("SUCCESS", msg);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Board add(@RequestBody Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(board.getId() != 0){
            throw new Exception("잘못된 요청입니다.\n변경 요청은 method=PUT 방식으로 호출하여 주세요.");
        }
        return boardRepository.save(board);
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Board modify(@PathVariable Long id, @RequestBody Board board, HttpServletRequest request, HttpServletResponse response) throws Exception {
        getBoard(id);

        board.setId(id);
        return boardRepository.save(board);
    }


    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Board remove(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        getBoard(id);
        boardRepository.delete(id);
        return sucessBoardMsg("ID : ["+id+ "] 삭제되었습니다.");
    }
}