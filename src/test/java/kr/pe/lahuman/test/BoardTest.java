package kr.pe.lahuman.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.pe.lahuman.Application;
import kr.pe.lahuman.board.Board;
import kr.pe.lahuman.board.BoardDto;
import kr.pe.lahuman.board.BoardService;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by lahuman on 2015-09-21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BoardTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BoardService service;

    MockMvc mocMvc;



    @Before
    public void setUp(){
        mocMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void createAccount() throws Exception{
        BoardDto.Create createDto = getBoardCreateDto();

        ResultActions result = mocMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isCreated());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is("lahuman")));

        // Bad Request
        createDto.setBody("I ");

        result = mocMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is("bad.request")));

    }

    private BoardDto.Create getBoardCreateDto() {
        BoardDto.Create createDto = new BoardDto.Create();
        createDto.setAuthor("lahuman");
        createDto.setPasswd("1234");
        createDto.setTitle("title - 1");
        createDto.setBody("I made Board CRUD Program.\n" +
                "That time I didn't understand JPA.\n" +
                "So I am refactoring it.\n" +
                "\n" +
                "I follow \"https://github.com/keesun/amugona\".\n");
        return createDto;
    }

    @Test
    public void getBoards() throws Exception {
        BoardDto.Create createDto = getBoardCreateDto();
        service.createBoard(createDto);

        Pageable pageable = new PageRequest(1, 5);

        ResultActions result = mocMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pageable)));


        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isOk());
        //{"content":[{"author":"lahuman","title":"title - 1","body":"I made Board CRUD Program.\nThat time I didn't understand JPA.\nSo I am refactoring it.\n\nI follow \"https://github.com/keesun/amugona\".\n","createDate":1442817128582},{"author":"lahuman","title":"lahuman-2","body":"jamesgoslingNo worries.\njamesgoslingIs it cold now in korea?","createDate":1442817128771,"updateDate":1442817128838},{"author":"lahuman","title":"title - 1","body":"I made Board CRUD Program.\nThat time I didn't understand JPA.\nSo I am refactoring it.\n\nI follow \"https://github.com/keesun/amugona\".\n","createDate":1442817128973}],"last":true,"totalElements":3,"totalPages":1,"size":20,"number":0,"numberOfElements":3,"first":true}
        result.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements",CoreMatchers.is(3)));
    }

    @Test
    public void getBoard() throws Exception {
        BoardDto.Create createDto = getBoardCreateDto();
        Board board = service.createBoard(createDto);

        ResultActions result = mocMvc.perform(MockMvcRequestBuilders.get("/api/posts/"+board.getId()));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateBoard() throws Exception {
        BoardDto.Create createDto = getBoardCreateDto();
        Board board = service.createBoard(createDto);

        BoardDto.Update updateDto = new BoardDto.Update();
        updateDto.setPasswd("1234");
        updateDto.setTitle("lahuman-2");
        updateDto.setBody("jamesgoslingNo worries.\n" +
                "jamesgoslingIs it cold now in korea?");

        ResultActions result = mocMvc.perform(MockMvcRequestBuilders.put("/api/posts/" + board.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto)));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isOk());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is("lahuman-2")));

        //Wrong password
        updateDto.setPasswd("1234asdfasd");
        updateDto.setTitle("lahuman-3");
        result = mocMvc.perform(MockMvcRequestBuilders.put("/api/posts/" + board.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is("board.wrong.password.exception")));

    }

    @Test
    public void deleteBoard() throws Exception{
        BoardDto.Create createDto = getBoardCreateDto();
        Board board = service.createBoard(createDto);

        BoardDto.Delete deleteDto = new BoardDto.Delete();
        deleteDto.setPasswd(board.getPasswd());

        ResultActions result = mocMvc.perform(MockMvcRequestBuilders.delete("/api/posts/"+board.getId())
                                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(deleteDto)));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isNoContent());

        //Wrong password
        board = service.createBoard(createDto);
        deleteDto.setPasswd("0000");

        result = mocMvc.perform(MockMvcRequestBuilders.delete("/api/posts/"+board.getId())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(deleteDto)));

        result.andDo(MockMvcResultHandlers.print());
        result.andExpect(MockMvcResultMatchers.status().isBadRequest());
        result.andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is("board.wrong.password.exception")));


    }
}
