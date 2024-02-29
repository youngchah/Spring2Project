package kr.or.ddit.controller.crud;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.service.IBoardService;
import kr.or.ddit.vo.Board;

@Controller
@RequestMapping("/crud/board")
public class CrudBoardController {
	
	@Inject
	private IBoardService service;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String crudRegisterForm() {
		
		return "crud/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String crudRegister(Board board, Model model) {
		service.register(board);
		
		// 방법 1 - 등록 이후 결과 페이지(success)로 보내는 방법
//		model.addAttribute("msg", "등록이 완료되었습니다!");
//		return "crud/success";
		
		// 방법 2 - 등록 이후 상세 보기 페이지로 보내는 방법
		return "redirect:/crud/board/read?boardNo="+board.getBoardNo();
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String crudList(Model model) {
		List<Board> boardList = service.list();
		model.addAttribute("boardList", boardList);
		return "crud/list";
		
	}
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String crudRead(int boardNo, Model model) {
		Board board = service.read(boardNo);
		model.addAttribute("board", board);
		return "crud/read";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String crudModifyForm(int boardNo, Model model) {
		Board board = service.read(boardNo);
		model.addAttribute("board", board);
		model.addAttribute("status", "u");	//수정을 진행중이라는 flag값
		
		return "crud/register";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String crudModify(Board board, Model model) {
		service.update(board);
		model.addAttribute("msg", "수정이 완료되었습니다!");
		return "crud/success";
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String crudRemove(int boardNo, Model model) {
		service.delete(boardNo);
		model.addAttribute("msg", "삭제가 완료되었습니다!");
		return "crud/success";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String crudSearch(String title, Model model) {
		Board board = new Board();
		board.setTitle(title);
		
		List<Board> boardList = service.search(board);
		model.addAttribute("boardList", boardList);
		model.addAttribute("board", board);
		return "crud/list";
	}
}













