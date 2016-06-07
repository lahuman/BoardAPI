package kr.pe.lahuman.board;

import kr.pe.lahuman.common.ErrorResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lahuman on 2015-06-23.
 */
@RestController
@RequestMapping(value="/api/posts")
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping( method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public PageImpl<BoardDto.Response> list(Pageable pageable) throws Exception {
        Page<Board> page = boardService.findAll(pageable);
        List<BoardDto.Response> content = page.getContent().stream()
                .map(board -> modelMapper.map(board, BoardDto.Response.class))
                .collect(Collectors.toList());
        return new PageImpl<BoardDto.Response>(content, pageable, page.getTotalElements());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public BoardDto.Response view(@NonNull @PathVariable Long id) throws Exception {
        Board board = boardService.getBoard(id);
        return modelMapper.map(board, BoardDto.Response.class);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createBoard(@RequestBody @Valid BoardDto.Create create, BindingResult result) throws Exception {
        if(result.hasErrors()){
           return validation(result);
        }
        Board board = boardService.createBoard(create);
        return new ResponseEntity(modelMapper.map(board, BoardDto.Response.class), HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity updateBoard(@NonNull @PathVariable Long id, @RequestBody @Valid BoardDto.Update updateDto, BindingResult result) throws Exception {
        if(result.hasErrors()){
          return validation(result);
        }
        Board updateBoard = boardService.update(id, updateDto);
        return new ResponseEntity(modelMapper.map(updateBoard, BoardDto.Response.class), HttpStatus.OK);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@NonNull @PathVariable Long id, @RequestBody @Valid BoardDto.Delete deleteDto, BindingResult result) throws Exception {
        if(result.hasErrors()){
           return validation(result);
        }
        boardService.delete(id, deleteDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    //Excpetion handler
    @ExceptionHandler(BoardException.BoardNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBoardNotFoundException(BoardException.BoardNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "] dose not have it!");
        errorResponse.setCode("board.not.found.exception");
        return errorResponse;
    }

    @ExceptionHandler(BoardException.BoardWrongPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleWrongPasswordException(BoardException.BoardWrongPasswordException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getId() + "] wrong password!!");
        errorResponse.setCode("board.wrong.password.exception");
        return errorResponse;
    }

    private ResponseEntity validation(BindingResult result) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Wrong request!");
		errorResponse.setCode("bad.request");
		List<kr.pe.lahuman.common.ErrorResponse.FieldError> errorList = new ArrayList<>();
		for (Object object : result.getAllErrors()) {
		    if(object instanceof FieldError) {
		    	kr.pe.lahuman.common.ErrorResponse.FieldError fe = new kr.pe.lahuman.common.ErrorResponse.FieldError();
		        FieldError fieldError = (FieldError) object;
		        fe.setField(fieldError.getField());
		        fe.setCode(fieldError.getCode());
		        fe.setMessage(fieldError.getDefaultMessage());
		        errorList.add(fe);
		    }
		}
		errorResponse.setErrors(errorList);
		return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
