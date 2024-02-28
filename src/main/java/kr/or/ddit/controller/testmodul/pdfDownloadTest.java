package kr.or.ddit.controller.testmodul;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class pdfDownloadTest {
	
	@RequestMapping(value = "/pdfDownload", method = RequestMethod.GET)
	public String pdfDownload(Model model) {
		log.info("pdfDownload() 실행..!");
		
		return "pdf/pdfDownload";
	}
}