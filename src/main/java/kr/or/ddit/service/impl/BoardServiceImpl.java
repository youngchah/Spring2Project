package kr.or.ddit.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.controller.crud.BoardRecordNotFoundException;
import kr.or.ddit.mapper.IBoardMapper;
import kr.or.ddit.service.IBoardService;
import kr.or.ddit.vo.Board;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements IBoardService {
	
	@Inject
	private IBoardMapper mapper;
	
	@Override
	public void register(Board board) {
		log.info("## BoardServiceImpl register()");
		mapper.create(board);
	}

	@Override
	public List<Board> list() {
		log.info("## BoardServiceImpl list()");
		return mapper.list();
	}

	@Override
	public Board read(int boardNo) throws Exception {
		log.info("## BoardServiceImpl read()");
		
		Board board = mapper.read(boardNo);
		
		if(board == null) {
			throw new BoardRecordNotFoundException("Not Found boardNo = " + boardNo);
		}
		
		return board;
	}

	@Override
	public void update(Board board) {
		log.info("## BoardServiceImpl update()");
		mapper.update(board);
	}

	@Override
	public void delete(int boardNo) {
		log.info("## BoardServiceImpl delete()");
		mapper.delete(boardNo);
	}

	@Override
	public List<Board> search(Board board) {
		log.info("## BoardServiceImpl search()");
		return mapper.search(board);
	}

}
