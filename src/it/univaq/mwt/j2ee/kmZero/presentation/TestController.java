package it.univaq.mwt.j2ee.kmZero.presentation;

import it.univaq.mwt.j2ee.kmZero.business.BusinessException;
import it.univaq.mwt.j2ee.kmZero.business.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class TestController {
	
	@Autowired
	private TestService service;
	
	@RequestMapping(value="/testNumberOne")
	public String testNumberOne(Model model) throws BusinessException {
		service.testNumberOne();	
		model.addAttribute("test", 1);
		return "common.test";
	}
	
	@RequestMapping(value="/testNumberTwo")
	public String testNumberTwo(Model model) throws BusinessException {
		service.testNumberTwo();	
		model.addAttribute("test", 2);
		return "common.test";
	}
	
	@RequestMapping(value="/testNumberThree")
	public String testNumberThree(Model model) throws BusinessException {
		service.testNumberThree();	
		model.addAttribute("test", 3);
		return "common.test";
	}
	
	@RequestMapping(value="/testNumberFour")
	public String testNumberFour(Model model) throws BusinessException {
		service.testNumberFour();	
		model.addAttribute("test", 4);
		return "common.test";
	}

	@RequestMapping(value="/testNumberFive")
	public String testNumberFive(Model model) throws BusinessException {
		service.testNumberFive();	
		model.addAttribute("test", 5);
		return "common.test";
	}
}
