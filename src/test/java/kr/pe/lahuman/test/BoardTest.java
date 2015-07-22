package kr.pe.lahuman.test;

import kr.pe.lahuman.Application;
import kr.pe.lahuman.board.Board;
import kr.pe.lahuman.board.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;


/**
 * Created by lahuman on 2015-06-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class BoardTest {


    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void add(){

        boardRepository.save(new Board("광규", "안녕하세요.", "방갑습니다. 앞으로 잘 부탁 드려요."));
        boardRepository.save(new Board("광규", "안녕하세요.", "방갑습니다. 앞으로 잘 부탁 드려요."));
        boardRepository.save(new Board("광규", "안녕하세요.", "방갑습니다. 앞으로 잘 부탁 드려요."));
        boardRepository.save(new Board("광규", "안녕하세요.", "방갑습니다. 앞으로 잘 부탁 드려요."));

        List<Board> list = getBoards();
        Assert.assertEquals(list.size(), 4);
    }
    @Test
    public void remove(){
        List<Board> list = getBoards();
        int size = list.size();
        boardRepository.delete(list.get(0).getId());

        list = getBoards();

        Assert.assertEquals(list.size(), size-1);
    }

    private List<Board> getBoards() {
        return boardRepository.findAll();
    }

    @Test
    public void update()  {
        List<Board> list = getBoards();
        Assert.assertNotEquals(list.size(), 0);

        Board board = list.get(0);
        log.debug(board.toString());

        board.setTitle("안녕~");
        boardRepository.save(board);

        Board updateData = boardRepository.findOne(board.getId());

        log.debug(updateData.toString());
        Assert.assertEquals(updateData.getTitle(), "안녕~");
    }

    @Test(expected = RuntimeException.class)
    public void error1(){
        boardRepository.save(new Board());
        //Exception 발생하지 않으면 실패
        Assert.assertTrue(false);
    }

    @Test(expected = Exception.class)
    public void error2(){
        boardRepository.save(new Board("", "안녕하세요.", "방갑습니다. 앞으로 잘 부탁 드려요."));

        //Exception 발생하지 않으면 실패
        Assert.assertTrue(false);
    }
}
